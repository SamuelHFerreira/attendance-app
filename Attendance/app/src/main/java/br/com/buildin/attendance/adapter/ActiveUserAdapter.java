package br.com.buildin.attendance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.buildin.attendance.R;
import br.com.buildin.attendance.model.ActiveUser;
import br.com.buildin.attendance.model.SellerStatus;
import br.com.buildin.attendance.model.StatusSessionForm;
import br.com.buildin.attendance.model.UserStatus;
import br.com.buildin.attendance.service.AttendanceService;
import br.com.buildin.attendance.service.body.SellerBody;
import br.com.buildin.attendance.util.CurrencyMask;

/**
 * Created by samuelferreira on 29/12/16.
 */
public class ActiveUserAdapter extends ArrayAdapter<ActiveUser> {

    private final List<ActiveUser> activeUsers;
    private final List<View> views;

    private LayoutInflater inflator;
    private List<ViewHolder> lstHolders;
    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                long currentTime = System.currentTimeMillis();
                for (ViewHolder holder : lstHolders) {
                    holder.updateTimeRemaining(currentTime);
                }
            }
        }
    };

    public ActiveUserAdapter(Context context,
                             List<ActiveUser> activeUsers) {
        super(context, -1, activeUsers);
        this.activeUsers = activeUsers;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.views = new ArrayList<>();
        this.lstHolders = new ArrayList<>();
        startUpdateTimer();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.active_user_list_item, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.firstLine);
            holder.timeCounterTextView= (TextView) convertView.findViewById(R.id.secondLine);
            ImageView avatarImageView = (ImageView) convertView.findViewById(R.id.icon);
            final Button finishSessionButton = (Button) convertView.findViewById(R.id.finish_attendance);
            final Button removeVendorButton = (Button) convertView.findViewById(R.id.remove_user);
            final Button timeWaitButton = (Button) convertView.findViewById(R.id.wait_attendance);

            final ViewGroup parentView = parent;
            final int positionItem = position;

            finishSessionButton.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    sessionButtonHandler(v, parentView, positionItem);
                    swichStartButtonBackground(finishSessionButton, positionItem);

                }
            });

            removeVendorButton.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    removeVendorButtonHandler(v, parentView, positionItem);

                }
            });

            timeWaitButton.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    timeWaitButtonHandler(v, parentView, positionItem);

                }
            });

//            holder.titleTextView.setText(activeUsers.get(position).getTitle());
            // TODO set avatar icons and ok icons according to the state
//        String s = titles.get(position);
//        imageView.setImageResource(R.drawable.no);
            views.add(position, convertView);
            convertView.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(getItem(position));

        return convertView;
    }

    public void swichStartButtonBackground(View finishSessionButton, Integer positionItem) {
        boolean shouldEnableButton = true;
        if (getItem(positionItem).getUserStatus() == UserStatus.IN_ATTENDANCE)
            finishSessionButton.setBackgroundResource(R.drawable.ic_finish_attendance);
        if (getItem(positionItem).getUserStatus() == UserStatus.IDLE)
            finishSessionButton.setBackgroundResource(R.drawable.ic_start_attendance);
        if (getItem(positionItem).getUserStatus() == UserStatus.AWAY)
            shouldEnableButton = finishSessionButton.isEnabled() ? false : true ;
//        finishSessionButton.setActivated(shouldEnableButton);
//        finishSessionButton.setEnabled(shouldEnableButton);
    }

    public String getTimeCounterValue(int position) {
        View rowView = views.get(position);
        TextView secondLineTextView = (TextView) rowView.findViewById(R.id.secondLine);
        return secondLineTextView.getText().toString();
    }

    public void sessionButtonHandler(View view, ViewGroup parent, int position) {
        if (getItem(position).getUserStatus() == UserStatus.IN_ATTENDANCE)
            finishSessionAction(view, parent, position);
        else
            startSessionAction(view, parent, position);
    }

    private void finishSessionAction(final View view, final ViewGroup parent, final int position) {
        Snackbar.make(parent, "Finalizar atendimento", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        builder.setTitle("Finalizar atendimento");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.finish_session_popup, parent, false);
        final EditText descriptionText = (EditText) viewInflated.findViewById(R.id.description_text);
        final CheckBox hasBoughtCheckBox = (CheckBox) viewInflated.findViewById(R.id.has_bought_something);
        final CheckBox testedProductCheckBox = (CheckBox) viewInflated.findViewById(R.id.has_tested_product);
        EditText purchaseText = (EditText) viewInflated.findViewById(R.id.purchase_value);
        purchaseText.addTextChangedListener(new CurrencyMask(purchaseText));
        final EditText finalPurchaseText = purchaseText;

        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Log.v("adapter", "finished modal");
                StatusSessionForm form = new StatusSessionForm();
                form.setDescription(descriptionText.getText().toString());
                form.setHasBoughtSomething(hasBoughtCheckBox.isChecked());
                form.setHasTestedProduct(testedProductCheckBox.isChecked());
                form.setPurchaseValue(parseString(finalPurchaseText.getText().toString()));

                AttendanceService.instance(parent.getContext()).finishSession(getItem(position).getId(), form);
                getItem(position).setUserStatus(UserStatus.IDLE);
                swichStartButtonBackground(view, position);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getItem(position).setUserStatus(UserStatus.IN_ATTENDANCE);
                swichStartButtonBackground(view, position);
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void startSessionAction(View view, ViewGroup parent, int position) {
        AttendanceService.instance(parent.getContext()).startAttendance(getItem(position).getId(), this, view, position);
    }

    private void removeVendorButtonHandler(View view, ViewGroup parent, int position) {
        AttendanceService.instance(parent.getContext()).logoutUser(getItem(position).getId(), this, position);
    }

    private void timeWaitButtonHandler(View view, ViewGroup parent, int position) {
        ActiveUser activeUser = getItem(position);
        SellerStatus sellerStatusToUpdate = activeUser.getUserStatus() == UserStatus.AWAY ? SellerStatus.IN_QUEUE : SellerStatus.ABSENT;
        AttendanceService.instance(parent.getContext()).updateSeller(activeUser.getId(), new SellerBody(sellerStatusToUpdate), this, view, position);
    }

    private BigDecimal parseString(String value) {
        BigDecimal result = null;
        try {
            value = value.replace("R$", "");
            value = value.replace(".", "");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            String pattern = "#,#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);
            result = (BigDecimal) decimalFormat.parse(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }
}

class ViewHolder {
    TextView titleTextView;
    TextView timeCounterTextView;
    ActiveUser activeUser;

    public void setData(ActiveUser item) {
        activeUser = item;
        titleTextView.setText(item.getTitle());
        updateTimeRemaining(System.currentTimeMillis());
    }

    public void updateTimeRemaining(long currentTime) {
        long timeDiff = currentTime - activeUser.getStartTimestamp();
        if (timeDiff > 0 && activeUser.getUserStatus() == UserStatus.IN_ATTENDANCE) {
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
            timeCounterTextView.setText(hours + " hrs " + minutes + " mins " + seconds + " seg");
        } else {
            timeCounterTextView.setText("--");
        }
    }
}