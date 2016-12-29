package br.com.buildin.attendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.com.buildin.attendance.R;

/**
 * Created by samuelferreira on 29/12/16.
 */
public class ActiveUserAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> objects;

    public ActiveUserAdapter(Context context,
                             List<String> objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.active_user_list_item, parent, false);
        TextView firstLineTextView = (TextView) rowView.findViewById(R.id.firstLine);
        ImageView avatarImageView = (ImageView) rowView.findViewById(R.id.icon);
        firstLineTextView.setText(objects.get(position));
        // TODO set avatar icons and ok icons according to the state
//        String s = objects.get(position);
//        imageView.setImageResource(R.drawable.no);

        return rowView;
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