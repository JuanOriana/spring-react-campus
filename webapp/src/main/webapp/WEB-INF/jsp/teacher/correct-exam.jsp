<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="../config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="../components/course-sections-col.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
                <jsp:param name="year" value="${course.year}"/>
                <jsp:param name="quarter" value="${course.quarter}"/>
                <jsp:param name="code" value="${course.subject.code}"/>
                <jsp:param name="board" value="${course.board}"/>
                <jsp:param name="itemId" value="${4}"/>
            </jsp:include>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"><spring:message code="teacher.correct.exam.section-heading" htmlEscape="true"/></h3>
                <div class="big-wrapper">
                    <c:if test="${answers.size() == 0}">
                        <spring:message code="teacher.correct.exam.none.to.correct" htmlEscape="true"/>
                    </c:if>
                    <c:forEach var="answer" items="${answers}">
                        <c:set var="answer" value="${answer}" scope="request"/>
                        <jsp:include page="../components/student-exam-unit.jsp">
                            <jsp:param name="examId" value="${examId}"/>
                            <jsp:param name="isCorrected" value="${answer.score != null}"/>
                        </jsp:include>
                    </c:forEach>
            <%--<h3 style="margin: 10px 0;"><spring:message code="teacher.correct.exam.to.correct" htmlEscape="true"/></h3>
            <c:if test="${uncorrectedAnswers.size() == 0}">
                <spring:message code="teacher.correct.exam.none.to.correct" htmlEscape="true"/>
            </c:if>
            <c:forEach var="uncorrectedAnswer" items="${uncorrectedAnswers}">
                <c:set var="answer" value="${uncorrectedAnswer}" scope="request"/>
                <jsp:include page="../components/student-exam-unit.jsp">
                    <jsp:param name="examId" value="${examId}"/>
                </jsp:include>
            </c:forEach>

            <c:if test="${correctedAnswers.size() != 0}">
            <h3 style="margin: 10px 0;"><spring:message code="teacher.correct.exam.corrected" htmlEscape="true"/></h3>
                <c:forEach var="correctedAnswer" items="${correctedAnswers}">
                    <c:set var="answer" value="${correctedAnswer}" scope="request"/>
                    <jsp:include page="../components/student-exam-unit.jsp">
                        <jsp:param name="examId" value="${examId}"/>
                        <jsp:param name="isCorrected" value="${true}"/>
                    </jsp:include>
                </c:forEach>
            </c:if> --%>
                </div>
            </div>
        </div>
    <div class="pagination-wrapper" style="font-size: 18px">
        <c:if test="${currentPage > 1}">
        <a href="<c:url value="/course/${course.courseId}/exam/${examId}?page=${currentPage-1}&pageSize=${pageSize}"/>">
        <img src="<c:url value="/resources/images/page-arrow.png"/>"
             alt="<spring:message code="img.alt.next.page" />" class="pagination-arrow x-rotated small-icon">
        </a>
        </c:if>
        <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
        <c:if test="${currentPage < maxPage}">
        <a href="<c:url value="/course/${course.courseId}/exam/${examId}?page=${currentPage+1}&pageSize=${pageSize}"/>">
        <img src="<c:url value="/resources/images/page-arrow.png"/>"
             alt="<spring:message code="img.alt.next.page" />" class="pagination-arrow small-icon">
        </a>
        </c:if>
        </div>
        </div>
        <jsp:include page="../components/footer.jsp"/>
    </div>
</body>
</html>
