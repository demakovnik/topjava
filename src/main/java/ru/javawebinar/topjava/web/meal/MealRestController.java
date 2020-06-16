package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;


@Controller
public class MealRestController {
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {

        this.service = service;
    }

    public Collection<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        return MealsUtil.getTos(service.getAll(userId), caloriesPerDay);
    }

    public Collection<MealTo> getAllfilteredByDateAndTime (LocalDate startDate,
                                                          LocalTime startTime,
                                                          LocalDate endDate,
                                                          LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        LocalDate finalStartDate = startDate == null ?
                LocalDate.MIN : startDate;
        LocalTime finalStartTime = startTime == null ?
                LocalTime.MIN : startTime;
        LocalDate finalEndDate = endDate == null ?
                LocalDate.MAX : endDate;
        LocalTime finalEndTime = endTime == null ?
                LocalTime.MAX : endTime;
        return MealsUtil.getTos(service.getAll(userId).stream()
                .map(Meal::getDateTime)
                .filter(localDateTime -> {
                    LocalDate currentDate = localDateTime.toLocalDate();
                    LocalTime currentTime = localDateTime.toLocalTime();
                    return (currentDate.compareTo(finalStartDate) >= 0
                            && currentDate.compareTo(finalEndDate) <= 0) &&
                            (currentTime.compareTo(finalStartTime) >= 0
                                    && (currentTime.compareTo(finalEndTime) < 0));
                }).map(LocalDateTime::toLocalDate)
                .distinct()
                .map(localDate -> service.getAllByDate(localDate, userId))
                .flatMap(Collection::stream).collect(Collectors.toList()), caloriesPerDay);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        ValidationUtil.checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}