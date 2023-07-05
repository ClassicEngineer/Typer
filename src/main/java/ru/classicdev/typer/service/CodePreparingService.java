package ru.classicdev.typer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.classicdev.typer.domain.Code;
import ru.classicdev.typer.util.BufferedReaderUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CodePreparingService {


    public Code prepareFileToFormat(MultipartFile file, Code code) {
        StringBuilder raw = new StringBuilder();
        var escaped = BufferedReaderUtil.readAndMapByLine(file, (line) -> {
            raw.append(line).append("\n");
            return escapeHtmlSymbols(line);
        });

        code.setRaw(raw.toString());
        code.setPreparedToFormat(escaped.toString());

        return code;

    }

    public Code prepareCodeToType(Code code) {
        StringBuilder result = new StringBuilder();
        result.append("'");
        String[] lines = code.getFormatted().split("\n");
        for (String line : lines)
            if (!line.isBlank()) {
                line = insertNewLineIfNeeded(line);
                result.append(line);
            } else {
                result.append(line).append( "\\n");
            }
        result.append("'");
        code.setPreparedToType(result.toString());
        return code;
    }

    private static String insertNewLineIfNeeded(String line) {
        int bracesStart = line.lastIndexOf("{");
        String newLine = "\\n";
        if (bracesStart != -1) {
            return insertStringToStringAfterPos(line, bracesStart, newLine);
        }
        int bracesEnd = line.lastIndexOf("}");
        if (bracesEnd != -1) {
            return insertStringToStringAfterPos(line, bracesEnd, newLine);
        }
        int expressionEnd = line.lastIndexOf(";");
        if (expressionEnd != -1) {
            return insertStringToStringAfterPos(line, expressionEnd, newLine);
        }
        int annotationEnd = line.contains("@") ? findAnnotationEnd(line) : -1;
        if (annotationEnd != -1) {
            return insertStringToStringAfterPos(line, annotationEnd, newLine);
        }

        return line;
    }

    private static String insertStringToStringAfterPos(String line, int pos, String s) {
        return line.substring(0, pos + 1) + s + line.substring(pos + 1);
    }

    private static int findAnnotationEnd(String line) {
        int annotationStart = line.indexOf("@");
        for (int i = annotationStart + 1; i < line.length(); i++) {
            if (!Character.isAlphabetic(line.charAt(i))) {
                return i - 1;
            }
        }
        return -1;
    }

    /*
      escape html things
   */
    private String escapeHtmlSymbols(String codeLine) {
        codeLine = codeLine.replaceAll("<", "&lt");
        codeLine = codeLine.replaceAll(">", "&gt");
        return codeLine;
    }

}
