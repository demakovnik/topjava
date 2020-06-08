package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
    private MealStorage mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new MemoryMealStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null ||
                !(action.equals("add") ||
                        action.equals("delete") ||
                        action.equals("edit"))) {
            final int calories = 2000;
            log.debug("get all meals");
            request.setAttribute("meals", MealsUtil.filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX, calories));
            request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal = null;
        int id;
        switch (action) {
            case "delete":
                id = Integer.valueOf(request.getParameter("id"));
                mealStorage.delete(id);
                response.sendRedirect("meals");
                return;
            case "add":
                meal = new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 0);
                break;
            case "edit":
                id = Integer.valueOf(request.getParameter("id"));
                meal = mealStorage.get(id);
                break;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(request.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("mealtime"));
        String description = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));
        Meal meal = new Meal(id, localDateTime, description, calories);
        mealStorage.save(meal);
        response.sendRedirect("meals");
    }
}


