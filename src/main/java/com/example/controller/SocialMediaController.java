package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.MessageService;
import com.example.service.AccountService;

import com.example.entity.Account;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    @Autowired(required = true)
    private AccountService accountService;
    @Autowired(required = true)
    private MessageService messageService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> accountRegistration(@RequestBody Account account) {
        if (account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }
        Account acc = accountService.registerAccount(account);
        if (acc == null)
            return ResponseEntity.status(409).build();
        return ResponseEntity.ok(acc);
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> accountLogIn(@RequestBody Account account) {
        Account acc = accountService.accountLogIn(account);
        if (acc == null)
            return ResponseEntity.status(401).build();
        return ResponseEntity.ok(acc);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessage_text().length() < 1 || message.getMessage_text().length() > 255
                || accountService.findAccountById(message.getPosted_by()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getAllMessageById(@PathVariable("message_id") Integer id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @DeleteMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") Integer id) {
        if (messageService.getMessageById(id) == null) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.ok(messageService.deleteMessageById(id));
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@RequestBody Message message,
            @PathVariable("message_id") Integer id) {
        Message messageDB = messageService.getMessageById(id);
        if (message.getMessage_text().length() < 1 || message.getMessage_text().length() > 255
                || messageDB == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(messageService.updateMessage(message, messageDB));
    }

    @GetMapping("accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesByUser(  @PathVariable("account_id") Integer account_id) {
        return ResponseEntity.ok(messageService.getUserMessages(account_id));
    }

}
