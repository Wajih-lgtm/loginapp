package com.ex.loginapp.util;

import com.ex.loginapp.model.User;

public class Validator {
    /**
     * يرجع رسالة الخطأ أو null إذا كان صحيحًا
     */
    public static String validate(User user) {
        if (user == null) return "خطأ داخلي";
        String username = user.getUsername();
        String password = user.getPassword();

        if (username == null || username.isEmpty()) return "اسم المستخدم فارغ";
        if (password == null || password.isEmpty()) return "كلمة المرور فارغة";
        if (username.length() > 6 || password.length() > 6)
            return "اسم المستخدم أو كلمة المرور يجب ألا تتجاوز 6 أحرف أو أرقام";
        return null; // صحيح
    }
}
