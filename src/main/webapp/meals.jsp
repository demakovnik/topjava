<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>

    <a href="meals?action=add">Add</a>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date and Time</th>
            <th>Calories</th>
            <th>Description</th>
            <th></th>
            <th></th>

        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color:${meal.excess ? "red" : "green"}">

                <td>${meal.dateTime.toLocalDate().format(dateFormatter)} ${meal.dateTime.toLocalTime().format(timeFormatter)}</td>
                <td>${meal.calories}</td>
                <td>${meal.description}</td>
                <td><a href="meals?action=delete&uuid=${meal.uuid}">Delete</a> </td>
                <td><a href="meals?action=edit&uuid=${meal.uuid}">Edit</a> </td>

            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>