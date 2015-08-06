package com.byoutline.secretsauce.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.dagger.SecretSauceModule;
import com.byoutline.secretsauce.utils.LogUtils;

import java.util.UUID;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class SimpleDialogV4 extends DialogFragment {
    private static final String TAG = LogUtils.internalMakeLogTag(SimpleDialogV4.class);
    private static final String ARG_DIALOG_VIEW_ID = "Dialog view id";
    private static final String ARG_DIALOG_VIEW_BUNDLE = "Dialog view bundle";
    private static final String ARG_DIALOG_UID = "Dialog uid";
    private static final int EMPTY_VIEW_ID = 0;
    private int viewId = EMPTY_VIEW_ID;
    private String uid = "";
    private Bundle viewArgs = new Bundle();

    public static SimpleDialogV4 newInstance(@LayoutRes int viewId, String uid) {
        return newInstance(viewId, uid, new Bundle());
    }

    public static SimpleDialogV4 newInstance(@LayoutRes int viewId, String uid, Bundle viewArgs) {
        SimpleDialogV4 instance = new SimpleDialogV4();
        Bundle args = new Bundle();
        args.putInt(ARG_DIALOG_VIEW_ID, viewId);
        args.putString(ARG_DIALOG_UID, uid);
        args.putParcelable(ARG_DIALOG_VIEW_BUNDLE, viewArgs);
        instance.setArguments(args);
        return instance;
    }

    private void unpackArgs() {
        Bundle args = getArguments();
        if (args == null) {
            logDialogWasCalledWithInvalidArgs(null);
            return;
        }

        viewId = args.getInt(ARG_DIALOG_VIEW_ID, EMPTY_VIEW_ID);
        uid = args.getString(ARG_DIALOG_UID, "");
        viewArgs = args.getParcelable(ARG_DIALOG_VIEW_BUNDLE);

        if (viewId == EMPTY_VIEW_ID || uid.isEmpty()) {
            logDialogWasCalledWithInvalidArgs(args);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_full_screen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        unpackArgs();
        View view = inflater.inflate(viewId, container, false);
        if (view instanceof DialogView) {
            DialogView dialogView = (DialogView) view;
            dialogView.setData(viewArgs, uid);
        }
        return view;
    }

    public static String getUid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        SecretSauceModule.dialogsManager.removeVisibleDialog(getUid());
        super.onDismiss(dialog);
    }

    private void logDialogWasCalledWithInvalidArgs(Bundle args) {
        LogUtils.LOGD(TAG, "Dialog called with invalid arguments: " + args);
    }
}
