package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.model.UserPrincipal;
import io.github.randomboi404.mboard.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final int PAGE_SIZE = 20;

    @GetMapping
    public Page<Message> getMessagePage(
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        return messageService.getMessages(PageRequest.of(page, PAGE_SIZE, Sort.by("dateTime").descending()));
    }

    @PostMapping
    public void saveMessage(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody String messageContent) {
        messageService.processAndBroadcast(userPrincipal, messageContent);
    }
    
}
