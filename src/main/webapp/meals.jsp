<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date and Time</th>
            <th>Calories</th>
            <th>Description</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color:<%=meal.isExcess() ? "red" : "green"%>">

            <td>${meal.dateTime.toLocalDate().format(dateTimeFormatter)} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.calories}</td>
            <td>${meal.description}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>