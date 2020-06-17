package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), 2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000), 2);
        save(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 300), 2);
        save(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед", 1200), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} {}", meal, userId);
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} {}", id, userId);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} {}", id, userId);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null ? userMeals.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return filterByPredicate(userId, meal -> {
            LocalDate currentDate = meal.getDate();
            return DateTimeUtil.isBetweenHalfOpen(currentDate, LocalDate.MIN, LocalDate.MAX);
        });
    }

    @Override
    public List<Meal> getAllByDate(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getAllByDate {} {} {}", startDate, endDate, userId);
        return filterByPredicate(userId, meal -> {
            LocalDate currentDate = meal.getDate();
            return DateTimeUtil.isBetweenHalfOpen(currentDate, startDate, endDate);
        });
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null ? userMeals
                .values()
                .stream()
                .filter(filter)
                .sorted(Comparator
                        .comparing(Meal::getDateTime)
                        .reversed())
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}

