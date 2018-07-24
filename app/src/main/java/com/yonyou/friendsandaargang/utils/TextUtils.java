package com.yonyou.friendsandaargang.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yonyou.friendsandaargang.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by shibing on 18/2/27.
 */

public class TextUtils {
    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isNullOrEmpty(String s) {
        if ((null == s) || ("").equals(s)) {
            return false;
        } else {
            return true;
        }
    }

    public static void Toast(Context context, String s) {
        /*View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
*/
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 400);
       /* toast.setView(view);
        textView.setText(s);*/
        toast.show();
    }

    public static boolean isQQ(String s) {
        if (s.matches("[1-9][0-9]{4,14}")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text,String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 多个关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text, String[] keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;

    }




}
