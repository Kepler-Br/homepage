package ru.keplerbr.homepage.data.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import ru.keplerbr.homepage.data.model.UriGeneratorTask;
import ru.keplerbr.homepage.data.repository.UriGeneratorTaskRepository;

@RequiredArgsConstructor
public class UriGenerator {

  private final String serviceName;

  private final UriGeneratorTaskRepository repository;


  private int takesBeforeIncrement = 15;

  private int takesBeforeGiveUp = 5;

  private Object targetEntity;

  private Consumer<Object> insertMethod;

  private Consumer<String> setUriMethod;

  UriGeneratorTask getOrCreate() {
    Optional<UriGeneratorTask> taskOptional = repository.findByServiceName(serviceName);

    return taskOptional.orElseGet(() -> repository.save(new UriGeneratorTask(serviceName)));
  }

  UriGeneratorTask increment(UriGeneratorTask task) {
    task = repository.getOne(task.getId());

    task.setUriLength(task.getUriLength() + 1);
    repository.save(task);
    return task;
  }

  UriGenerator takesUntilIncrement(int takes) {
    takesBeforeIncrement = takes;

    return this;
  }

  UriGenerator takesUntilGiveUp(int takes) {
    takesBeforeGiveUp = takes;

    return this;
  }

  UriGenerator setTargetEntity(Object targetEntity) {
    this.targetEntity = targetEntity;

    return this;
  }

  UriGenerator repositoryInsertMethod(Consumer<Object> method) {
    insertMethod = method;

    return this;
  }

  UriGenerator objectSetUriMethod(Consumer<String> setUriMethod) {
    this.setUriMethod = setUriMethod;

    return this;
  }

  void generate() {
    int takesGiveUp = takesBeforeGiveUp;
    UriGeneratorTask task = getOrCreate();

    while (takesGiveUp >= 0) {
      int takesIncrement = takesBeforeIncrement;

      while (takesIncrement >= 0) {
        String randomUrl = Utils.base64Random(task.getUriLength());

        setUriMethod.accept(randomUrl);
        insertMethod.accept(targetEntity);

        takesIncrement--;
      }

      takesGiveUp--;
    }
  }

}
