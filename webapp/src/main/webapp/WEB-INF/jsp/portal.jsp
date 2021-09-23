<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title><spring:message code="campus.page.title" htmlEscape="true"/></title>
        <c:import url="config/generalHead.jsp"/>
    </head>
    <body>
    <div class="page-organizer">
        <jsp:include page="components/navbar.jsp">
            <jsp:param name="navItem" value="${1}"/>
        </jsp:include>
        <div class="page-container">
            <h2 class="section-heading"><spring:message code="portal.section-heading.title" htmlEscape="true"/></h2>
            <div class="courses-container">
                <c:forEach var="courseItem" items="${courseList}">
                    <div class="course">
                        <p class="course-name">
                            <a href="<c:url value="course/${courseItem.courseId}"/>" class="styleless-anchor">
                                <spring:message code="subject.name" htmlEscape="true" arguments="${courseItem.subject.name}"/>
                            </a>
                        </p>
                        <p class="course-extra-info"><spring:message code="portal.course.info" htmlEscape="true" arguments="${courseItem.year},${courseItem.quarter}"/></p>
                    </div>
                </c:forEach>

            </div>
        </div>
        <jsp:include page="components/footer.jsp"/>
    </div>
    </body>
</html>