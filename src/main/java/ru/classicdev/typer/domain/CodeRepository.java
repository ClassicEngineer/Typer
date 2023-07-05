package ru.classicdev.typer.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

@Repository
public interface CodeRepository extends CrudRepository<Code, String> {


    Optional<Code> findByFileId(Long fileId);
}
