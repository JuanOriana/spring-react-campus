<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="timetable.page.title"/></title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
<jsp:include page="components/navbar.jsp">
    <jsp:param name="navItem" value="${4}"/>
</jsp:include>
<div class="page-container">
    <h2 class="section-heading"><spring:message code="timetable.section-heading.title"/></h2>
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
                            <td class="active-time-td"  style="padding: 0;background:${courseColors.get(currentCourse)}"
                                data-tooltip="${currentCourse.subject.name}">
                                <a class="styleless-anchor" style="display: block;height: 100%; padding: 1em; text-align: center"
                                   href="<c:url value="/course/${currentCourse.courseId}"/>">
                                        ${currentCourse.subject.code} [${currentCourse.board}]
                                </a>
                            </td>
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
    <jsp:include page="components/footer.jsp"/>
</div>

</body>
</html>