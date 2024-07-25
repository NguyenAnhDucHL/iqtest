package com.study.iqtest.mapper;
import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.model.IqTestSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IqTestSettingMapper {
    @Mapping(target = "questions", ignore = true)
    IqTestSettingDTO toDto(IqTestSetting iqTestSetting);
    IqTestSetting toModal(IqTestSettingDTO iqTestSettingDTO);
}
