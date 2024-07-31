package com.study.iqtest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.study.iqtest.serializer.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IqTestDTO {

    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId userId;
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId testSettingId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date testDate;
    private String status;
}
