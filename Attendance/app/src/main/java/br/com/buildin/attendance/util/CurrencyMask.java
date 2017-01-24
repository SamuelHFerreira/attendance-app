package br.com.buildin.attendance.util;

import android.icu.text.NumberFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by samuelferreira on 24/01/17.
 */
public class CurrencyMask implements TextWatcher {

    final EditText campo;

    public CurrencyMask(EditText campo) {
        super();
        this.campo = campo;
    }

    private boolean isUpdating = false;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int after) {
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        isUpdating = true;
        String str = s.toString();
        boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                (str.indexOf(".") > -1 || str.indexOf(",") > -1));
        if (hasMask) {
            str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                    .replaceAll("[.]", "");
        }

        try {
            str = nf.format(Double.parseDouble(str) / 100);
            campo.setText(str);
            campo.setSelection(campo.getText().length());
        } catch (NumberFormatException e) {
            s = "";
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // Não utilizado
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Não utilizado
    }
}
