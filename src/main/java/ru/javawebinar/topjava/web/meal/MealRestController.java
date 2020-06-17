package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;


@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealTo> getAll() {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        return MealsUtil.getTos(service.getAll(userId), caloriesPerDay);
    }

    public Collection<MealTo> getAllfilteredByDateAndTime(LocalDate startDate,
                                                          LocalTime startTime,
                                                          LocalDate endDate,
                                                          LocalTime endTime) {
        log.info("getAllfilteredByDateAndTime {} {} {} {}", startDate, startTime, endDate, endTime);
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        return MealsUtil.getFilteredTos(service.getAllByDate(startDate == null ?
                LocalDate.MIN : startDate, endDate == null ?
                LocalDate.MAX : endDate, userId), caloriesPerDay, startTime == null ?
                LocalTime.MIN : startTime, endTime == null ?
                LocalTime.MAX : endTime);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("get {}", meal);
        int userId = SecurityUtil.authUserId();
        ValidationUtil.checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} {}", meal, id);
        int userId = SecurityUtil.authUserId();
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}