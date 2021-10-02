<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title><spring:message code="campus.page.title"/></title>
        <c:import url="config/generalHead.jsp"/>
    </head>
    <body>
    <div class="page-organizer">
        <jsp:include page="components/navbar.jsp">
            <jsp:param name="navItem" value="${1}"/>
        </jsp:include>
        <div class="page-container">
            <h2 class="section-heading"><spring:message code="portal.section-heading.title"/></h2>
            <div class="courses-container">
                <c:forEach var="courseItem" items="${courseList}">
                    <div class="course">
                        <div class="course-name" style="display: flex; align-items: center">
                            <a href="<c:url value="course/${courseItem.courseId}"/>" class="styleless-anchor">
                                <spring:message code="portal.subject.board.name" htmlEscape="true" arguments="${courseItem.subject.name},${courseItem.board}"/>
                            </a>
                            <c:if test="${!coursesAsStudent.contains(courseItem)}">
                                <img src="<c:url value="/resources/images/graduation-hat.png"/>"
                                     alt="teacher" style="margin-left: 10px" width="28px"/>
                            </c:if>
                        </div>
                        <p class="course-extra-info"><spring:message code="portal.course.info" htmlEscape="true" arguments="${courseItem.year},${courseItem.quarter}"/></p>
                    </div>
                </c:forEach>

            </div>
        </div>
        <jsp:include page="components/footer.jsp"/>
    </div>
    </body>
</html>