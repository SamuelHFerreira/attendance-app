package br.com.buildin.attendance.fragment;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


import java.util.ArrayList;
import java.util.Arrays;

import br.com.buildin.attendance.R;

/**
 * Created by samuelferreira on 28/12/16.
 */
public class ActiveUserFragment extends ListFragment {
    private static final String LOG_TAG = ActiveUserFragment.class.getSimpleName();

    private static final int LIST_ITEM_COUNT = 5;

    private ArrayList<String> testArray = new ArrayList<>(
            Arrays.asList("Junior 1", "Junior 2", "jony"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.active_user_fragment, container, false);

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(com.android.internal.R.layout.list_content,
//                container, false);
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ListAdapter adapter = new ArrayAdapter<String>(
//                                                    getActivity(),
//                                                    R.layout.active_user_fragment,
//                                                    testArray);
//        setListAdapter(adapter);
    }
}