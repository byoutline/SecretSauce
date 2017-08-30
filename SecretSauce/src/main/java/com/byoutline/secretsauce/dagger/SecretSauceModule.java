package com.byoutline.secretsauce.dagger;

import com.byoutline.secretsauce.dialogs.DialogsManager;
import com.byoutline.secretsauce.models.WaitDialogModel;

public class SecretSauceModule {
    public static WaitDialogModel waitDialogModel = new WaitDialogModel();

    public static DialogsManager dialogsManager = new DialogsManager();
}
