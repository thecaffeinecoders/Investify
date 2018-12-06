package android.example.com.investify;

import android.annotation.TargetApi;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {
private final DecimalFormat df;
private final DecimalFormat dfnd;
private final EditText et;
private boolean hasFractionalPart;
private int trailingZeroCount;

@TargetApi(Build.VERSION_CODES.N)
public NumberTextWatcher(EditText editText, String pattern) {
        df = new DecimalFormat(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        this.et = editText;
        hasFractionalPart = false;
        }

@TargetApi(Build.VERSION_CODES.N)
@Override
public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);

        if (s != null && !s.toString().isEmpty()) {
        try {
        int inilen, endlen;
        inilen = et.getText().length();
        String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "").replace("$","");
            Number n = null;
            try {
                n = df.parse(v);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int cp = et.getSelectionStart();
        if (hasFractionalPart) {
        StringBuilder trailingZeros = new StringBuilder();
        while (trailingZeroCount-- > 0)
        trailingZeros.append('0');
        et.setText(df.format(n) + trailingZeros.toString());
        } else {
        et.setText(dfnd.format(n));
        }
        //et.setText("$".concat(et.getText().toString()));
        endlen = et.getText().length();
        int sel = (cp + (endlen - inilen));
        if (sel > 0 && sel < et.getText().length()) {
        et.setSelection(sel);
        } else if (trailingZeroCount > -1) {
        et.setSelection(et.getText().length() - 3);
        } else {
        et.setSelection(et.getText().length());
        }
        } catch (NumberFormatException e) {
        e.printStackTrace();
        }
        }

        et.addTextChangedListener(this);
        }

@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

@TargetApi(Build.VERSION_CODES.N)
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
        int index = s.toString().indexOf(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
        trailingZeroCount = 0;
        if (index > -1) {
        for (index++; index < s.length(); index++) {
        if (s.charAt(index) == '0')
        trailingZeroCount++;
        else {
        trailingZeroCount = 0;
        }
        }
        hasFractionalPart = true;
        } else {
        hasFractionalPart = false;
        }
        }
        }
