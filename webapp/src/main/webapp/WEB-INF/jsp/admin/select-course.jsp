<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="select.course.page.title"/></title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="isAdmin" value="${true}"/>
    </jsp:include>
    <div class="page-container">
        <form action="<c:url value="/admin/course/enroll"/>" class="form-wrapper reduced" method="get" css="margin: 30px 0">
            <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="select.course.header"/></h1>
            <label name="courseId" for="courseId" class="form-label"><spring:message code="select.course"/></label>
            <select name="courseId" id="courseId" class="form-input" style="font-size: 26px">
                <c:forEach var="course" items="${courses}">
                    <option value="${course.courseId}">
                        <spring:message code="select.course.name.board.year.quarter" htmlEscape="true" arguments="${course.subject.name},${course.board},${course.year},${course.quarter}"/>
                    </option>
                </c:forEach>
            </select>
            <button class="form-button"><spring:message code="select.course.button.add.users"/></button>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
