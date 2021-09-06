<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Timetable</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <link href="<c:url value = "${page.Context.request.contextPath}/resources/css/style.css" />" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap"
          rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp">
    <jsp:param name="navItem" value="${3}"/>
</jsp:include>
<div class="page-container">
    <h2 class="section-heading">Mis Horarios</h2>
    <div class="tab">
        <table class="timetable">
            <tr class="days">
                <th></th>
                <c:forEach items="${days}" var="day">
                    <th>${day}</th>
                </c:forEach>
            </tr>
            <c:forEach items="${hours}" var="hour" varStatus="hourLoop">
                <tr>
                    <td class="time">${hour}</td>
                    <c:forEach items="${days}" var="day" varStatus="dayLoop">
                        <c:set var = "currentCourse" value = "${timeTableMatrix.get(dayLoop.index).get(hourLoop.index)}"/>
                        <c:if test="${currentCourse != null}">
                            <td class="active-time-td" data-tooltip="${currentCourse.subject.name}">
                                    ${currentCourse.subject.code} [${currentCourse.board}]</td>
                        </c:if>
                        <c:if test="${currentCourse == null}">
                            <td></td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>