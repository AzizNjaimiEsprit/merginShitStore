package Dao;

import java.util.ArrayList;

public interface IService<T> {
    void add(T t);

    void delete(int id);

    void update(T t);

    T get(int id);

    ArrayList<T> get();
}
