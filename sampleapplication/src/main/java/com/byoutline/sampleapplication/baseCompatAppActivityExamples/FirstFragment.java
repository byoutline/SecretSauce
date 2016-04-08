package com.byoutline.sampleapplication.baseCompatAppActivityExamples;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.byoutline.sampleapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by michalp on 12.04.16.
 */
public class FirstFragment extends AbstractCustomFragment {

    @Bind(R.id.mainBtn)
    Button mainBtn;

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public String getFragmentActionbarName() {
        /**
         * here setup action bar title for you fragment
         */
        return getString(R.string.first_fragment);
    }

    @Override
    public boolean showBackButtonInActionbar() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.mainBtn)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Welcome First Time!");
        builder.setPositiveButton("OK",null);
        builder.show();
    }
}
