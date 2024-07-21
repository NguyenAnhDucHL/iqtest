package com.study.iqtest.mapper;

import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    User toModal(UserDTO userDTO);
}
