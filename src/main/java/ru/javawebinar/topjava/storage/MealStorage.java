package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    Meal get(int id);

    void delete(int id);

    Meal save(Meal meal);

    List<Meal> getAll();


}