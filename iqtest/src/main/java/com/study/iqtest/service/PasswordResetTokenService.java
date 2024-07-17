package com.study.iqtest.service;

import com.study.iqtest.dto.PasswordResetTokenDTO;
import com.study.iqtest.mapper.PasswordResetTokenMapper;
import com.study.iqtest.model.PasswordResetToken;
import com.study.iqtest.model.User;
import com.study.iqtest.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    @Autowired
    private UserService userService;

    public void save(PasswordResetTokenDTO tokenDTO) {
        PasswordResetToken token = passwordResetTokenMapper.toEntity(tokenDTO);
        tokenRepository.save(token);
    }

    public Optional<PasswordResetTokenDTO> findByToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        return resetToken.map(passwordResetTokenMapper::toDto);
    }

    public void delete(PasswordResetTokenDTO tokenDTO) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(tokenDTO.getToken());
        resetToken.ifPresent(tokenRepository::delete);
    }
}
