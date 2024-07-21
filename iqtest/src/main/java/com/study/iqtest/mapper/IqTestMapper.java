package com.study.iqtest.mapper;
import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.model.IqTest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IqTestMapper {
    IqTestSettingDTO toDto(IqTest iqTest);
    IqTest toModal(IqTestSettingDTO iqTestDTO);
}
