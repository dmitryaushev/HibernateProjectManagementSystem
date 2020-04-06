package com.jdbc.dao;

import java.util.List;

public interface DataAccessObject<T> {

    void create(T object);
    T getByID(int id);
    List<T> getAll();
    void update(T object);
    void delete(T object);
}
