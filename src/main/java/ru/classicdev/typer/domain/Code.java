package ru.classicdev.typer.domain;


import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Code")
public class Code implements Serializable {


    private String content;

    private String id;
}
