package com.example.demo.service;

import com.example.demo.constants.MessageStatus;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(MessageStatus.OPEN);
        contact.setCreatedBy(MessageStatus.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        log.info(contact.toString());
        int resultSet = contactRepository.insertContactData(contact);
        if(resultSet > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> getStatusMessage() {
        List<Contact> contactList = contactRepository.fetchDetailsBasedOnStatus(MessageStatus.OPEN);
        return contactList;
    }

    public void updateStatus(int id, String name) {
        contactRepository.updateStatusOfRecord(id, name,MessageStatus.CLOSE);
    }
}
