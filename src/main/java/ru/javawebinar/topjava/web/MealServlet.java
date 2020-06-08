package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
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
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MemoryStorage();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*final int calories = 2000;
        log.debug("get all meals");
        request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, calories));
        request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);*/

        String action = request.getParameter("action");
        if (action == null) {
            final int calories = 2000;
            log.debug("get all meals");
            request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, calories));
            request.setAttribute("dateFormatter", DATE_FORMATTER);
            request.setAttribute("timeFormatter", TIME_FORMATTER);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal = null;
        int uuid;
        switch (action) {

            case "delete":
                uuid = Integer.valueOf(request.getParameter("uuid"));
                storage.delete(uuid);
                response.sendRedirect("meals");
                return;

            case "add":
                meal = new Meal(LocalDateTime.now(), "", 0);
                break;

            case "edit":
                uuid = Integer.valueOf(request.getParameter("uuid"));
                meal = storage.get(uuid);
                break;


        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(
                ("/edit.jsp")
        ).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(request.getParameter("uuid"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("mealtime"));
        String description = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));
        Meal meal = new Meal(id, localDateTime, description, calories);
        storage.create(meal);
        response.sendRedirect("meals");
    }
}


