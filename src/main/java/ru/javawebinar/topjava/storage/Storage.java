package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.io.IOException;
import java.util.List;

public interface Storage {

    void clear();

    Meal get(String uuid);

    void update(Meal meal);

    void delete(String uuid);

    void save(Meal meal);

    List<Meal> getAll() throws IOException;

    int size();
}