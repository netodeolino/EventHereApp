package com.neto.deolino.trabalhoandroid.util;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neto.deolino.trabalhoandroid.R;

/**
 * Created by matheus on 07/12/16.
 */

public class EventFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        return  view;
    }
}
