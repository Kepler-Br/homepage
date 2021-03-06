package ru.keplerbr.homepage.graphql.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.graphql.data.model.Tag;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Set<Tag> findAllByNameIn(Set<String> name);

  Optional<Tag> findByName(String name);

  long deleteByName(String name);

  boolean existsByName(String name);
}
