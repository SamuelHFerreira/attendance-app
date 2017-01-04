package br.com.buildin.attendance.adapter;

import android.app.Activity;
import android.content.Context;
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

/**
 * Created by samuelferreira on 29/12/16.
 */
public class ActiveUserAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> titles;
    private final List<View> views;
    private final Activity callingActivity;

    public ActiveUserAdapter(Context context,
                             List<String> titles,
                             Activity callingActivity) {
        super(context, -1, titles);
        this.context = context;
        this.titles = titles;
        this.views = new ArrayList<>();
        this.callingActivity = callingActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.active_user_list_item, parent, false);
        TextView firstLineTextView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secondLineTextView = (TextView) rowView.findViewById(R.id.secondLine);
        addTimeCounter(secondLineTextView);

        ImageView avatarImageView = (ImageView) rowView.findViewById(R.id.icon);
        firstLineTextView.setText(titles.get(position));
        // TODO set avatar icons and ok icons according to the state
//        String s = titles.get(position);
//        imageView.setImageResource(R.drawable.no);
        views.add(position, rowView);
        return rowView;
    }

    public String getTimeCounterValue(int position) {
        View rowView = views.get(position);
        TextView secondLineTextView = (TextView) rowView.findViewById(R.id.secondLine);
        return secondLineTextView.getText().toString();
    }

    private void addTimeCounter(final TextView textView) {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                callingActivity.runOnUiThread(new Runnable()
                {
                    int count = 0;

                    @Override
                    public void run()
                    {
                        textView.setText("count="+count);
                        count++;
                    }
                });
            }
        }, 1000, 1000);
        // TODO if want to stop
//        T.cancel();
    }

//    @Override
//    public long getItemId(int position) {
//        String item = getItem(position);
//        return mIdMap.get(item);
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
}