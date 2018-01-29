package com.byoutline.secretsauce.lifecycle;


@FunctionalInterface
public interface AttachableViewModelDetachAction {
    void onDetach();
}
