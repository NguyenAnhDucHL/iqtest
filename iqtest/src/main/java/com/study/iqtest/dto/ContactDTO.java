package com.study.iqtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;
}