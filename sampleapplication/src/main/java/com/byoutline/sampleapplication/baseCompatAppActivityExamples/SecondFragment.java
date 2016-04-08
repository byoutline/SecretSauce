package com.byoutline.sampleapplication.baseCompatAppActivityExamples;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byoutline.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by michalp on 12.04.16.
 */
public class SecondFragment extends AbstractCustomFragment {

    @Override
    public String getFragmentActionbarName() {
        return "Dialog Fragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
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
        builder.setMessage("Welcome Second Time!");
        builder.setPositiveButton("OK",null);
        builder.show();
    }
}
