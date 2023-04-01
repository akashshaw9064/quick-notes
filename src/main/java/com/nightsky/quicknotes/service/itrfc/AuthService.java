package com.nightsky.quicknotes.service.itrfc;

import com.nightsky.quicknotes.model.AuthRequest;

public interface AuthService {
    String authenticate(AuthRequest request) throws Exception;
}
