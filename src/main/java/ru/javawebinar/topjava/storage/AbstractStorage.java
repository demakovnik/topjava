package ru.javawebinar.topjava.storage;


import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger log = getLogger(AbstractStorage.class);
    protected static final Comparator<Meal> COMPARATOR = Comparator.comparing(Meal::getDateTime);

    @Override
    public void update(Meal meal) {
        log.info("Update " + meal);
        updateByPointer(getPointerIfExistElement(meal.getUuid()), meal);
    }

    @Override
    public void save(Meal meal) {
        log.info("Save " + meal);
        insertIntoStorage(meal, getPointerIfNotExistElement(meal.getUuid()));
    }

    @Override
    public void delete(String uuid) {
        log.info("Delete " + uuid);
        deleteElementByPointer(getPointerIfExistElement(uuid));
    }

    @Override
    public Meal get(String uuid) {
        log.info("Get " + uuid);
        return getMealByPointer(getPointerIfExistElement(uuid));
    }

    private SK getPointerIfExistElement(String uuid) {
        SK pointer = getPointerToMeal(uuid);
        if (isExistPointer(pointer)) {
            return pointer;
        }
        log.warn("Meal " + uuid + " not exist");
        return null;

    }

    private SK getPointerIfNotExistElement(String uuid) {
        SK pointer = getPointerToMeal(uuid);
        if (isExistPointer(pointer)) {
            log.warn("Meal " + uuid + " already exist");
        }
        return pointer;
    }

    @Override
    public List<Meal> getAll() throws IOException {
        log.info("GetAllSorted");
        return getList().stream().sorted(COMPARATOR).collect(Collectors.toList());
    }

    protected abstract List<Meal> getList() throws IOException;

    protected abstract Meal getMealByPointer(SK pointer);

    protected abstract void deleteElementByPointer(SK pointer);

    protected abstract boolean isExistPointer(SK pointer);

    protected abstract SK getPointerToMeal(String uuid);

    protected abstract void insertIntoStorage(Meal resume, SK pointer);

    protected abstract void updateByPointer(SK pointer, Meal resume);
}