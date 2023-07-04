package ru.classicdev.typer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TyperService {

    /* extract file content
     escape html things
     save by sessionId
    */
    public void process(MultipartFile file, String sessionId) {


    }

    public String getCodeBySessionId(String sessionId) {

        return null;
    }
}
