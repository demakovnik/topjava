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

    public Collection<MealTo> getAllfilteredByDateAndTime(String startDateString,
                                                          String startTimeString,
                                                          String endDateString,
                                                          String endTimeString) {
        LocalDate startDate = startDateString.equals("") ?
                LocalDate.MIN : LocalDate.parse(startDateString);
        LocalTime startTime = startTimeString.equals("") ?
                LocalTime.MIN : LocalTime.parse(startTimeString);
        LocalDate endDate = endDateString.equals("") ?
                LocalDate.MAX : LocalDate.parse(endDateString);;
        LocalTime endTime = endTimeString.equals("") ?
                LocalTime.MAX : LocalTime.parse(endTimeString);;
        return getAll()
                .stream()
                .filter(mealTo -> {
                    LocalDateTime ldt = mealTo.getDateTime();
                    LocalDate currentDate = ldt.toLocalDate();
                    LocalTime currentTime = ldt.toLocalTime();
                    return (currentDate.compareTo(startDate) >= 0
                            && currentDate.compareTo(endDate) <= 0) &&
                            (currentTime.compareTo(startTime) >= 0
                                    && (currentTime.compareTo(endTime) < 0));
                }).collect(Collectors.toList());
    }


    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
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