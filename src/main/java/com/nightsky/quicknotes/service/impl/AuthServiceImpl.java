package com.nightsky.quicknotes.service.impl;

import com.nightsky.quicknotes.model.AuthRequest;
import com.nightsky.quicknotes.service.itrfc.AuthService;
import com.nightsky.quicknotes.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String authenticate(AuthRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            log.info("AUTHENTICATION SUCCESSFUL!");
        } catch (BadCredentialsException e) {
            log.error("AUTHENTICATION FAILURE!");
            throw new Exception("Incorrect user credentials!");
        }
        return jwtUtil.generateToken(request.getUsername());
    }
}
