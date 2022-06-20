package com.example.Liudiao;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class LimitInputTextWatcher implements TextWatcher {

    private EditText et = null;

    private String regex;

    private String DEFAULT_REGEX = "[^\u4E00-\u9FA5]";

    public LimitInputTextWatcher(EditText et) {
        this.et = et;
        this.regex = DEFAULT_REGEX;
    }

    public LimitInputTextWatcher(EditText et, String regex) {
        this.et = et;
        this.regex = regex;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        String inputStr = clearLimitStr(regex, str);
        et.removeTextChangedListener(this);
        // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
        s.replace(0, s.length(), inputStr.trim());
        et.addTextChangedListener(this);
    }

    /**
     * 清除不符合条件的内容
     *
     * @param regex
     * @return
     */
    private String clearLimitStr(String regex, String str) {
        return str.replaceAll(regex, "");
    }
}
