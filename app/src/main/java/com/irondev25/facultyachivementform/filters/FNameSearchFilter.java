package com.irondev25.facultyachivementform.filters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FNameSearchFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        Matcher matcher = pattern.matcher(source);
        if(!matcher.matches()){
            return "";
        }
        return source;
    }
}
