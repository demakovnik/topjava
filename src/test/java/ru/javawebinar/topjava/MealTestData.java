package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 1;
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL = new Meal(START_SEQ + 2, LocalDateTime.of(2020, 6, 1, 10, 0), "Updated", 500);

    public static final Meal MEAL_1 = new Meal(START_SEQ + 2, LocalDateTime.of(2020, 6, 1, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, 6, 1, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 6, 1, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 6, 2, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, 7, 2, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_6 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, 7, 2, 13, 0), "Обед", 1000);
    public static final Meal MEAL_7 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, 7, 2, 20, 0), "Ужин", 500);
    public static final Meal MEAL_8 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, 6, 1, 10, 0), "Завтрак Админа", 500);
    public static final Meal MEAL_9 = new Meal(START_SEQ + 10, LocalDateTime.of(2020, 6, 1, 13, 0), "Обед Админа", 1000);
    public static final Meal MEAL_10 = new Meal(START_SEQ + 11, LocalDateTime.of(2020, 6, 1, 20, 0), "Ужин Админа", 500);
    public static final Meal MEAL_11 = new Meal(START_SEQ + 12, LocalDateTime.of(2020, 6, 2, 0, 0), "Еда на граничное значение Админа", 100);
    public static final Meal MEAL_12 = new Meal(START_SEQ + 13, LocalDateTime.of(2020, 7, 1, 10, 0), "Завтрак Админа", 500);
    public static final Meal MEAL_13 = new Meal(START_SEQ + 14, LocalDateTime.of(2020, 7, 1, 13, 0), "Обед Админа", 1000);
    public static final Meal MEAL_14 = new Meal(START_SEQ + 15, LocalDateTime.of(2020, 7, 1, 20, 0), "Ужин Админа", 500);






    /*
    100002	2020-06-01 10:00:00.000000	Завтрак	500
100003	2020-06-01 13:00:00.000000	Обед	1000
100004	2020-06-01 20:00:00.000000	Ужин	500
100005	2020-06-02 00:00:00.000000	Еда на граничное значение	100
100006	2020-07-02 10:00:00.000000	Завтрак	500
100007	2020-07-02 13:00:00.000000	Обед	1000
100008	2020-07-02 20:00:00.000000	Ужин	500
100009	2020-06-01 10:00:00.000000	Завтрак Админа	500
100010	2020-06-01 13:00:00.000000	Обед Админа	1000
100011	2020-06-01 20:00:00.000000	Ужин Админа	500
100012	2020-06-02 00:00:00.000000	Еда на граничное значение Админа	100
100013	2020-07-01 10:00:00.000000	Завтрак Админа	500
100014	2020-07-01 13:00:00.000000	Обед Админа	1000
100015	2020-07-01 20:00:00.000000	Ужин Админа	500
*/


    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 7, 3, 5, 0), "new meal", 2000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(1);
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