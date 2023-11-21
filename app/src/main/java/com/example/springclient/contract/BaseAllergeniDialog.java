package com.example.springclient.contract;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.springclient.R;

import java.util.ArrayList;
import java.util.List;

public interface BaseAllergeniDialog {
    default void listenerAllergeni(List<CheckBox> checkBoxes, List<String> allergeni, boolean soloLettura) {
        for (CheckBox c : checkBoxes) {
            String valore = (String) c.getTag();
            if(soloLettura){
                c.setClickable(false);
            }
            if (allergeni.contains(valore)) {
                c.setChecked(true);
            }
            c.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    if (!allergeni.contains(valore)) {
                        allergeni.add(valore);
                    }
                } else {
                    allergeni.remove(valore);
                }
            });
        }
    }

    default void dialogAllergeni(Context context, List<String> allergeni, boolean soloLettura) {
        Dialog dialogAllergeni = new Dialog(context);
        List<CheckBox> checkBoxes = new ArrayList<>();
        dialogAllergeni.setContentView(R.layout.dialog_tabella_allergeni);
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxFiltroTabellaAllergene));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxAnidrideSolforosa));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxCrostacei));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxFruttaGuscio));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxGlutine));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxLatte));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxLupini));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxMolluschi));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxPesce));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxSedano));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxSenape));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxSesamo));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxSoia));
        checkBoxes.add(dialogAllergeni.findViewById(R.id.checkBoxUova));
        Button buttonOkDialog = dialogAllergeni.findViewById(R.id.buttonOkTabellaAllergeniDialog);
        buttonOkDialog.setOnClickListener(view -> {
            dialogAllergeni.dismiss();
        });
        if(allergeni == null){
            allergeni = new ArrayList<>();
        }
        listenerAllergeni(checkBoxes,allergeni, soloLettura);
        dialogAllergeni.show();
    }

}
