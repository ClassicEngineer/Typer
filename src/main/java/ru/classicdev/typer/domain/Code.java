package ru.classicdev.typer.domain;


import org.springframework.data.redis.core.RedisHash;

@RedisHash("Code")
public class Code {

    private String content;

    private String id;
}
