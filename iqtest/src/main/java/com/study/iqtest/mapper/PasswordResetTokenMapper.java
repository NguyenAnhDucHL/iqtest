package com.study.iqtest.mapper;

import com.study.iqtest.dto.PasswordResetTokenDTO;
import com.study.iqtest.model.PasswordResetToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordResetTokenMapper {
    PasswordResetTokenDTO toDto(PasswordResetToken entity);

    PasswordResetToken toModal(PasswordResetTokenDTO dto);
}
