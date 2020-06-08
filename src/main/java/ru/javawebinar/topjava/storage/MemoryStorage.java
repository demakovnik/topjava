package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryStorage implements Storage {

    private Map<Integer, Meal> storage = new HashMap<>();

    private int generatedId = 0;

    {
        MealsUtil.MEALS.stream().forEach(meal -> create(meal));
    }

    @Override
    public Meal get(int uuid) {
        return storage.get(uuid);
    }

    @Override
    public void delete(int uuid) {
        storage.remove(uuid);

    }

    @Override
    public Meal create(Meal meal) {
        int uuid = meal.getUuid();
        if (uuid == -1) {
            uuid = generatedId++;
            meal.setUuid(uuid);
        }
        return storage.put(uuid,meal);
    }

    @Override
    public List<Meal> getAll() {
        return storage.values().stream().collect(Collectors.toList());
    }
}
