package com.lgadetsky.nodekeeper.server;

import java.util.List;

/**
 * Interface that defines the functionality of the DAO
 * 
 * @author Leonid Gadetsky
 *
 * @param <T>
 */
public interface DAO<T> {
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(int id);
}
