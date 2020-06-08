package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    Meal get(int uuid);

    void delete(int uuid);

    Meal create(Meal meal);

    List<Meal> getAll();


}