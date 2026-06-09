package com.ironhack.sanctuary.controller;

import com.ironhack.sanctuary.service.SanctuaryAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class SanctuaryAiController {

    private final SanctuaryAiService sanctuaryAiService;

    @GetMapping("/advice/{conversationId}")
    public String getAdvice(
            @PathVariable String conversationId,
            @RequestParam Long animalId) {

        return sanctuaryAiService.getCareAdvice(conversationId, animalId);
    }
}