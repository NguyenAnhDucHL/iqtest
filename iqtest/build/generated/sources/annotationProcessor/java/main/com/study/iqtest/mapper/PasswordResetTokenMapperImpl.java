package com.study.iqtest.mapper;

import com.study.iqtest.dto.PasswordResetTokenDTO;
import com.study.iqtest.model.PasswordResetToken;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-19T17:03:39+0700",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PasswordResetTokenMapperImpl implements PasswordResetTokenMapper {

    @Override
    public PasswordResetTokenDTO toDto(PasswordResetToken entity) {
        if ( entity == null ) {
            return null;
        }

        PasswordResetTokenDTO passwordResetTokenDTO = new PasswordResetTokenDTO();

        passwordResetTokenDTO.setToken( entity.getToken() );
        passwordResetTokenDTO.setEmail( entity.getEmail() );
        passwordResetTokenDTO.setExpiryDate( entity.getExpiryDate() );

        return passwordResetTokenDTO;
    }

    @Override
    public PasswordResetToken toModal(PasswordResetTokenDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setToken( dto.getToken() );
        passwordResetToken.setEmail( dto.getEmail() );
        passwordResetToken.setExpiryDate( dto.getExpiryDate() );

        return passwordResetToken;
    }
}
