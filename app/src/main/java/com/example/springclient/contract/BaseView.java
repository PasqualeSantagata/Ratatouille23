package com.example.springclient.contract;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.springclient.R;

import java.util.Collections;
import java.util.List;

public interface BaseView {
    Context getContext();
    void initializeComponents();

    default void mostraDialogErrore(Context context, String messaggio){
        Dialog dialogAttenzione = new Dialog(context);
        dialogAttenzione.setContentView(R.layout.dialog_err_one_btn);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewErrorMessageDialogErrorOneBtn);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogErrorOneBtn);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }
    default void mostraDialogWarningTwoBtn(Dialog dialogWarning, String messaggio, View.OnClickListener eventoSi, View.OnClickListener eventoNo) {
        dialogWarning.setContentView(R.layout.dialog_warning_two_button);

        TextView messaggiodialog = dialogWarning.findViewById(R.id.textViewDialogeWarnTwoBtn);
        messaggiodialog.setText(messaggio);

        Button buttonSi = dialogWarning.findViewById(R.id.buttonSiDialogWarnTwoBtn);
        Button buttonNo = dialogWarning.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        dialogWarning.show();

        if(eventoSi != null && eventoNo != null) {
            buttonSi.setOnClickListener(eventoSi);
            buttonNo.setOnClickListener(eventoNo);
        }else{
            dialogWarning.dismiss();
        }
    }


}