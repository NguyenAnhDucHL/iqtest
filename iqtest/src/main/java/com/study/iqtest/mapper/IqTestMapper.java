package com.study.iqtest.mapper;
import com.study.iqtest.dto.IqTestDTO;
import com.study.iqtest.model.IqTest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IqTestMapper {
    IqTestMapper INSTANCE = Mappers.getMapper(IqTestMapper.class);

    IqTestDTO toDto(IqTest iqTest);

    IqTest toModal(IqTestDTO iqTestDTO);
}
