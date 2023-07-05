package ru.classicdev.typer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.classicdev.typer.domain.Code;
import ru.classicdev.typer.domain.CodeNotFoundException;
import ru.classicdev.typer.domain.CodeRepository;

@Service
@RequiredArgsConstructor
public class TyperService {


    private final CodeRepository codeRepository;
    private final CodePreparingService codePreparingService;


    /* extract file content
      map lines
     save by sessionId
    */
    public void initProcess(MultipartFile file, String sessionId) {
        Code code = Code.builder()
                .id(sessionId)
                .build();

        code = codePreparingService.prepareFileToFormat(file, code);

        codeRepository.save(code);
    }


    /**
        @return code prepared to type in ome line
     */
    public String prepareToType(String formatted, String sessionId) {
        Code code = getCodeBySessionId(sessionId);

        code.setFormatted(formatted);

        code = codePreparingService.prepareCodeToType(code);

        codeRepository.save(code);

        return code.getPreparedToType();
    }

    public Code getCodeBySessionId(String sessionId) {
        return codeRepository.findById(sessionId)
                .orElseThrow(() -> new CodeNotFoundException("Not found code by sessionId:" + sessionId));
    }

    public Code getCodeBySessionOrByFileId(String sessionId, Long fileId) {
        return codeRepository.findById(sessionId)
                .or(() -> codeRepository.findByFileId(fileId))
                .orElseThrow(() -> new CodeNotFoundException("Not found code by sessionId: " + sessionId +" and fileId: " + fileId ));
    }

    public String getCodePreparedToFormat(String sessionId, Long fileId) {
        return getCodeBySessionOrByFileId(sessionId, fileId).getPreparedToFormat();
    }

    public String getCodePreparedToType(String sessionId) {
        return getCodeBySessionId(sessionId).getPreparedToType();
    }
}
