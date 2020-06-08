<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Save/Update Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Save/Update Meals</h2>

<section>
    <form method="post" enctype="application/x-www-form-urlencoded">
        <input id="mealid" type="hidden" name="mealid" value="${meal.id}">
        <label for="mealtime">Enter a date and time:</label>
        <input id="mealtime" type="datetime-local" name="mealtime" value="${meal.dateTime}">
        <label for="description">Enter description:</label>
        <input id="description" type="text" name="description" value="${meal.description}">
        <label for="calories">Enter Calories:</label>
        <input id="calories" type="number" name="calories" value="${meal.calories}">
        <button type="submit">Save</button>
        <button type="reset" onclick="window.history.back()">Cancel</button>
    </form>

</section>
</body>
</html>