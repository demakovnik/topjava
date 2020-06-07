package ru.javawebinar.topjava.storage;


import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Meal> storage = new ArrayList<>();

    @Override
    protected Meal getMealByPointer(Integer pointer) {
        int index = pointer;
        return storage.get(index);
    }

    @Override
    protected void deleteElementByPointer(Integer pointer) {
        int index = pointer;
        storage.remove(index);
    }

    @Override
    protected boolean isExistPointer(Integer pointer) {
        return pointer != null;
    }

    @Override
    protected Integer getPointerToMeal(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void insertIntoStorage(Meal meal, Integer pointer) {
        storage.add(meal);
    }

    @Override
    protected void updateByPointer(Integer pointer, Meal meal) {
        storage.set(pointer, meal);
    }

    @Override
    public void clear() {
        storage.clear();

    }

    @Override
    protected List<Meal> getList() {
        return storage.subList(0, size());
    }

    @Override
    public int size() {
        return storage.size();
    }

}
