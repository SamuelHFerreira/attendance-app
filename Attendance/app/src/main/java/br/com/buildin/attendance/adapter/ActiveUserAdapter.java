package br.com.buildin.attendance.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.buildin.attendance.R;
import br.com.buildin.attendance.model.ActiveUser;

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

    public void finishSessionButtonHandler(View view) {


    }

    public void removeVendorButtonHandler(View view) {

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