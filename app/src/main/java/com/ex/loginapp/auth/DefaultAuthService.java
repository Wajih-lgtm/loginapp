package com.ex.loginapp.auth;

import com.ex.loginapp.model.User;
import com.ex.loginapp.model.AuthenticationResult;

public class DefaultAuthService implements AuthService {
    // هنا وضعنا بيانات ثابتة كمثال؛ في الواقع ستتصل بقاعدة بيانات أو API
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";

    @Override
    public AuthenticationResult authenticate(User user) {
        if (user == null) {
            return new AuthenticationResult(false, "خطأ داخلي");
        }

        if (ADMIN_USER.equals(user.getUsername()) && ADMIN_PASS.equals(user.getPassword())) {
            return new AuthenticationResult(true, "تم تسجيل الدخول");
        } else {
            return new AuthenticationResult(false, "بيانات غير صحيحة");
        }
    }
}
