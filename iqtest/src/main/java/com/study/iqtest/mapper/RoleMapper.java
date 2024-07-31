package com.study.iqtest.mapper;

import com.study.iqtest.dto.RoleDTO;
import com.study.iqtest.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDto(Role role);
    Role toModal(RoleDTO roleDTO);
}
