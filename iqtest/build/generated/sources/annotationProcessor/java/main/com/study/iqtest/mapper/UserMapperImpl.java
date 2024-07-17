package com.study.iqtest.mapper;

import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T21:55:46+0700",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setRoleId( user.getRoleId() );
        try {
            if ( user.getCreatedAt() != null ) {
                userDTO.setCreatedAt( new SimpleDateFormat().parse( user.getCreatedAt() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( user.getUpdatedAt() != null ) {
                userDTO.setUpdatedAt( new SimpleDateFormat().parse( user.getUpdatedAt() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setName( userDTO.getName() );
        user.setEmail( userDTO.getEmail() );
        user.setPassword( userDTO.getPassword() );
        user.setPhoneNumber( userDTO.getPhoneNumber() );
        user.setRoleId( userDTO.getRoleId() );
        if ( userDTO.getCreatedAt() != null ) {
            user.setCreatedAt( new SimpleDateFormat().format( userDTO.getCreatedAt() ) );
        }
        if ( userDTO.getUpdatedAt() != null ) {
            user.setUpdatedAt( new SimpleDateFormat().format( userDTO.getUpdatedAt() ) );
        }

        return user;
    }
}
