package com.bluesky.shopping.oauth.service;

import com.bluesky.shopping.oauth.util.AuthToken;

public interface AuthService {

    AuthToken login(String username, String password, String clientId, String clientSecret);
}
