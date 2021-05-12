package ru.keplerbr.homepage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.data.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Tag findByName(String name);
}
