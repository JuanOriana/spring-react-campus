<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="components/course-sections-col.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
                <jsp:param name="year" value="${course.year}"/>
                <jsp:param name="quarter" value="${course.quarter}"/>
                <jsp:param name="code" value="${course.subject.code}"/>
                <jsp:param name="board" value="${course.board}"/>
                <jsp:param name="itemId" value="${4}"/>
            </jsp:include>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="course-exams.section-heading.title"/> </h3>
                <div class="big-wrapper">
                    <h3 style="margin: 10px 0;"><spring:message code="course-exams.comment"/></h3>
                    <c:if test="${unresolvedExams.size() == 0}">
                        <spring:message code="course-exams.no.exams"/>
                    </c:if>
                    <c:forEach var="unresolvedExam" items="${unresolvedExams}">
                        <c:set var="exam" value="${unresolvedExam}" scope="request"/>
                        <jsp:include page="./components/exam-unit.jsp"/>
                    </c:forEach>
                    <c:if test="${answerMarks.size() != 0}">
                        <h4 class="section-heading" style="margin-left:10px" >Average: <c:out value="${average}"/> </h4>
                        <h3 style="margin: 10px 0;"><spring:message code="course-exams.sent.exams"/></h3>
                        <c:forEach var="answer" items="${answerMarks}">
                            <c:set var="exam" value="${answer.exam}" scope="request"/>
                            <c:set var="answer" value="${answer}" scope="request"/>
                            <jsp:include page="./components/exam-unit.jsp">
                                <jsp:param name="isDelivered" value="${true}"/>
                            </jsp:include>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>

