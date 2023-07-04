package ru.classicdev.typer.service;

public class CodeNotFoundException extends RuntimeException {

    public CodeNotFoundException(String message) {
        super(message);
    }
}
