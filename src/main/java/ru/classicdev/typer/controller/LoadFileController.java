package ru.classicdev.typer.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.classicdev.typer.service.TyperService;

@Log
@Controller
@RequiredArgsConstructor
public class LoadFileController {

    private final TyperService typerService;

    @GetMapping("/load")
    public String loadPage() {
        return "load";
    }

    @PostMapping(value = "/load", consumes = "multipart/form-data")
    public String load(@RequestParam("file") MultipartFile file, HttpSession session) {
        log.info("File was uploaded!");
        typerService.process(file, session.getId());
        return "redirect:load";
    }

    @GetMapping("/coding")
    public String codingPage(Model model, HttpSession session) {
        model.addAttribute("code", typerService.getCodeBySessionId(session.getId()));
        return "coding";
    }


    @EventListener({ContextRefreshedEvent.class})
    public void logHrefOnStartup() {
        log.info("Go at http://localhost:8080/load");
    }
}
