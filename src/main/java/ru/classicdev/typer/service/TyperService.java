package ru.classicdev.typer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.classicdev.typer.domain.Code;
import ru.classicdev.typer.domain.CodeRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class TyperService {


    private final CodeRepository codeRepository;

    /* extract file content
     escape html things
     apply code formatting
     save by sessionId
    */
    public void process(MultipartFile file, String sessionId) {
        String line;
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            line = br.readLine();
            while (line != null) {
                line = br.readLine();
                result.append(processLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Code code = new Code();
        code.setId(sessionId);
        code.setContent(result.toString());

        codeRepository.save(code);
    }

    private String processLine(String codeLine) {
        return null;
    }

    public String getCodeBySessionId(String sessionId) {
        Code code = codeRepository.findById(sessionId)
                .orElseThrow(() -> new CodeNotFoundException("Not found code by sessionId"));
        return code.getContent();
    }
}
