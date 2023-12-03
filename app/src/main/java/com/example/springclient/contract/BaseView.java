package com.example.springclient.contract;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.springclient.R;

public interface BaseView {
    void inizializzaComponenti();
    void tornaIndietro();
    void mostraPorgressBar();
    void nascondiProgressBar();
    boolean isVisibile();

    default void mostraDialogErroreOneBtn(Dialog dialogAttenzione, String messaggio, View.OnClickListener eventoOk){
        dialogAttenzione.setContentView(R.layout.dialog_err_one_btn);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewErrorMessageDialogErrorOneBtn);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogErrorOneBtn);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(eventoOk);
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
    default void mostraDialogOkOneBtn(Dialog dialogOk, String messaggio, View.OnClickListener eventoOk) {
        dialogOk.setContentView(R.layout.dialog_ok_one_button);
        TextView textViewMess = dialogOk.findViewById(R.id.textViewDialogOkTwoBtn);
        textViewMess.setText(messaggio);
        Button buttonOk = dialogOk.findViewById(R.id.okDialog);
        buttonOk.setOnClickListener(eventoOk);

        dialogOk.show();
    }

    default void mostraDialogWarningOneBtn(Dialog dialogWarning, String messaggio, View.OnClickListener eventoOk){
        dialogWarning.setContentView(R.layout.dialog_warning_one_button);
        TextView errorMessage = dialogWarning.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        Button ok = dialogWarning.findViewById(R.id.buttonOkDialogueErrorOneBt);
        errorMessage.setText(messaggio);
        ok.setOnClickListener(eventoOk);
        dialogWarning.show();
    }


}