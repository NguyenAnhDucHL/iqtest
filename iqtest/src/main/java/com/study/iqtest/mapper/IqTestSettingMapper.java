package com.study.iqtest.mapper;
import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.model.IqTestSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IqTestSettingMapper {
    IqTestSettingDTO toDto(IqTestSetting iqTestSetting);
    IqTestSetting toEntity(IqTestSettingDTO iqTestSettingDTO);
}
