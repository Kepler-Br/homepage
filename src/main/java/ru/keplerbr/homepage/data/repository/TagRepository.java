package ru.keplerbr.homepage.data.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.data.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByName(String name);

  long deleteByName(String name);

}
