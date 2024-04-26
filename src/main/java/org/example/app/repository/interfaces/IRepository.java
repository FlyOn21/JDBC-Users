package org.example.app.repository.interfaces;

import org.example.app.utils.ActionAnswer;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    ActionAnswer create(T obj);
    Optional<List<T>> readAll(List<String> excludeColumns, int limit, int offset);
    ActionAnswer update(T obj);
    ActionAnswer delete(Long id);
    Optional<List<T>> readById(Long id, List<String> excludeColumns);
}
