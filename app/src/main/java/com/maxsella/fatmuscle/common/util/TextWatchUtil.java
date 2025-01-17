package com.maxsella.fatmuscle.common.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatchUtil {
    public static void addTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(s.length());
            }
        });
    }
}
