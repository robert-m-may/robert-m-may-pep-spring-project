package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
@Service
public class MessageService {
    @Autowired
    private MessageRepository MessageService;

    public Message createMessage(Message message) {
        return MessageService.save(message);
    }

    public List<Message> getAllMessages() {
        return MessageService.findAll();
    }

    public Message getMessageById(Integer id) {
        return MessageService.findById(id).orElse(null);
    }

    public Integer deleteMessageById(Integer id) {
        MessageService.deleteById(id);
        return 1;
    }

    public Integer updateMessage(Message message, Message messageDB) {
        messageDB.setMessage_text(message.getMessage_text());
        MessageService.save(messageDB);
        return 1;
    }

    public List<Message> getUserMessages(Integer account_id) {
        return MessageService.findByPostedBy(account_id);
    }
}
