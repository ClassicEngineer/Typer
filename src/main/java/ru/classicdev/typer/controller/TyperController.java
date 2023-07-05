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
public class TyperController {

    private final TyperService typerService;

    @GetMapping("/load")
    public String loadPage() {
        return "load";
    }

    @PostMapping(value = "/load", consumes = "multipart/form-data")
    public String load(@RequestParam("file") MultipartFile file, HttpSession session) {
        log.info("File was uploaded!");
        typerService.initProcess(file, session.getId());
        return "redirect:coding";
    }

//@PathVariable(value = "file_id", required = false) Long fileId,
    @GetMapping("/coding")
    public String codingPage(Model model, HttpSession session) {
        model.addAttribute("code", typerService.getCodePreparedToFormat(session.getId(), null));
        return "coding";
    }

    @PostMapping(value = "/type", consumes = "application/json")
    public String type(@RequestBody FormattedCodeRequest request, Model model, HttpSession session) {
        log.info("Formatted Code Request was sent!");
        typerService.prepareToType(request.getFormattedCode(), session.getId());
        return "typing";
    }

    @GetMapping("/typing")
    public String typingPage(Model model, HttpSession session) {
        String code = typerService.getCodePreparedToType(session.getId());
        model.addAttribute("codePreparedToType", code);
        return "typing";
    }

    @EventListener({ContextRefreshedEvent.class})
    public void logHrefOnStartup() {
        log.info("Go at http://localhost:8080/load");
    }


}
