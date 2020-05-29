package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreamsOptional(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesPerDate = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            sumOfCaloriesPerDate.put(date, sumOfCaloriesPerDate.getOrDefault(date, 0) + userMeal.getCalories());
        }
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            boolean excess = sumOfCaloriesPerDate.get(date) > caloriesPerDay;
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess));
            }
        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesPerDate = meals.stream().collect(Collectors.groupingBy(userMeal ->
                userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return meals
                .stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(),
                        startTime,
                        endTime))
                .map(userMeal ->
                        new UserMealWithExcess(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                sumOfCaloriesPerDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional(List<UserMeal> meals,
                                                                     LocalTime startTime,
                                                                     LocalTime endTime,
                                                                     int caloriesPerDay) {
        return meals.stream().collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream()
                .flatMap(userMeals -> {
                    int sumOfCalories = userMeals.stream().mapToInt(UserMeal::getCalories).sum();
                    return userMeals
                            .stream()
                            .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(),
                                    startTime,
                                    endTime))
                            .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),
                                    userMeal.getDescription(),
                                    userMeal.getCalories(),
                                    sumOfCalories > caloriesPerDay));
                })
                .collect(Collectors.toList());
    }
}
