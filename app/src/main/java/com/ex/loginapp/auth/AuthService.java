package com.ex.loginapp.auth;

import com.ex.loginapp.model.User;
import com.ex.loginapp.model.AuthenticationResult;

public interface AuthService {
    AuthenticationResult authenticate(User user);
}
