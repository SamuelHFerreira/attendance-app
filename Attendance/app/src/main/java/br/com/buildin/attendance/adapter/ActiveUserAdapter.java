package br.com.buildin.attendance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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
import br.com.buildin.attendance.model.FinishSessionForm;
import br.com.buildin.attendance.service.AttendanceService;
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
            Button finishSessionButton = (Button) convertView.findViewById(R.id.finish_attendance);

            final ViewGroup parentView = parent;
            finishSessionButton.setOnClickListener(new View.OnClickListener()  {

                @Override
                public void onClick(View v) {
                    finishSessionButtonHandler(v, parentView);
                }
            });

            holder.titleTextView.setText(activeUsers.get(position).getTitle());
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

    public String getTimeCounterValue(int position) {
        View rowView = views.get(position);
        TextView secondLineTextView = (TextView) rowView.findViewById(R.id.secondLine);
        return secondLineTextView.getText().toString();
    }

    public void finishSessionButtonHandler(View view, ViewGroup parent) {
        Snackbar.make(view, "Finalizar atendimento", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
//

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                FinishSessionForm form = new FinishSessionForm();
                form.setDescription(descriptionText.getText().toString());
                form.setHasBoughtSomething(hasBoughtCheckBox.isChecked());
                form.setHasTestedProduct(testedProductCheckBox.isChecked());
                form.setPurchaseValue(parseString(finalPurchaseText.getText().toString()));
                AttendanceService.finishSession(form);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void removeVendorButtonHandler(View view) {

    }

    private BigDecimal parseString(String value) {
        BigDecimal result = null;
        try {
            value = value.replace("$", "");
            value = value.replace("R", "");
            value = value.replace(".", "");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            String pattern = "#.#";
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
        if (timeDiff > 0) {
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
            timeCounterTextView.setText(hours + " hrs " + minutes + " mins " + seconds + " seg");
        } else {
            timeCounterTextView.setText("--");
        }
    }
}