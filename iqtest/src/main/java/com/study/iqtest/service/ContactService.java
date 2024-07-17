package com.study.iqtest.service;

import com.study.iqtest.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

}
