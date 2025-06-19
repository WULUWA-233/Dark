package dao;

import java.util.List;

public interface IBaseDAO<T> {
    void add(T t);
    void delete(int id);
    void update(T t);
    T getById(int id);
    List<T> getAll();
}