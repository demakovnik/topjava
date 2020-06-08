package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {

    private ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    private AtomicInteger generatedId = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public Meal save(Meal meal) {
        Integer id = meal.getId();
        if (id == -1) {
            id = generatedId.getAndIncrement();
            meal.setId(id);
        }
        id = meal.getId();
        return storage.put(id, meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
