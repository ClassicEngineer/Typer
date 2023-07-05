package ru.classicdev.typer.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash("Code")
public class Code implements Serializable {


    private String id;

    private String raw;

    private String preparedToFormat;

    private String formatted;

    private String preparedToType;

}
