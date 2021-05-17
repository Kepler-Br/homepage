package ru.keplerbr.homepage.data.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.data.model.UriGeneratorTask;

public interface UriGeneratorTaskRepository extends JpaRepository<UriGeneratorTask, Long> {

  Optional<UriGeneratorTask> findByServiceName(String serviceName);

  boolean existsByServiceName(String serviceName);
}
