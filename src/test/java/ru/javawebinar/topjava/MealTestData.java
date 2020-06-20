package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL = new Meal(MEAL_ID, LocalDateTime.of(2020, 6, 1, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_1 = new Meal(100002, LocalDateTime.of(2020, 6, 1, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(100003, LocalDateTime.of(2020, 6, 1, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(100004, LocalDateTime.of(2020, 6, 1, 20, 0), "Ужин", 500);


    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "new meal", 2000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}