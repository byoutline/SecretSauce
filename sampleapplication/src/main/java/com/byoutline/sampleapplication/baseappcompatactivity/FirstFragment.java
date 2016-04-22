package com.byoutline.sampleapplication.baseappcompatactivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.byoutline.sampleapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by michalp on 12.04.16.
 */
public class FirstFragment extends AbstractCustomFragment {

    @BindView(R.id.mainBtn)
    Button mainBtn;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public String getFragmentActionbarName() {
        // Setup action bar title for fragment
        return getString(R.string.first_fragment);
    }

    @Override
    public boolean showBackButtonInActionbar() {
        return false;
    }

    @OnClick(R.id.mainBtn)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Welcome First Time!");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
