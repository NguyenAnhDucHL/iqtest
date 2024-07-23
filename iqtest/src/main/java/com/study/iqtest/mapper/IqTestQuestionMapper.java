package com.study.iqtest.mapper;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.model.IqTestQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {IqTestAnswerMapper.class})
public interface IqTestQuestionMapper {

    @Mapping(target = "answers", ignore = true)
    IqTestQuestionDTO toDto(IqTestQuestion iqTestQuestion);

    IqTestQuestion toModal(IqTestQuestionDTO questionDTO);
}
