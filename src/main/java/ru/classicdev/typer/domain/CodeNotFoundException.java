package ru.classicdev.typer.domain;

public class CodeNotFoundException extends RuntimeException {

    public CodeNotFoundException(String message) {
        super(message);
    }
}
