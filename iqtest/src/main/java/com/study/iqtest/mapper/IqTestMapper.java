package com.study.iqtest.mapper;
import com.study.iqtest.dto.IqTestDTO;
import com.study.iqtest.model.IqTest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IqTestMapper {
    IqTestDTO toDto(IqTest iqTest);
    IqTest toModal(IqTestDTO iqTestDTO);
}
