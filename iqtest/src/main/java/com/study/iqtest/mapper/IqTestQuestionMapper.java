package com.study.iqtest.mapper;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.model.IqTestQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IqTestQuestionMapper {
    IqTestQuestionDTO toDto(IqTestQuestion iqTestQuestion);
    IqTestQuestion toModal(IqTestQuestionDTO questionDTO);
}
