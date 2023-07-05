package ru.classicdev.typer.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public final class BufferedReaderUtil {


    public static StringBuilder readAndMapByLine(MultipartFile input, Function<String, String> lineMapper) {
        String line;
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input.getInputStream()))) {
            line = br.readLine();
            while (line != null) {
                result.append(lineMapper.apply(line));
                result.append("\n");
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
