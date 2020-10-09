package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.db.query.Query;

import java.util.List;

public interface Dao<T> {

    List<T> findAll();

    void save(T t);

    int count();

    <E> E findByQuery(Query<E> query);

}
