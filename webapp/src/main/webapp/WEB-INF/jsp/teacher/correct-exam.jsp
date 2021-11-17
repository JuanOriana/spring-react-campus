<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true"
                           arguments="${course.subject.name}"/></title>
    <c:import url="../config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true"
                                                    arguments="${course.subject.name}"/></h2>
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
                <h3 class="section-heading" style="margin: 0 0 20px 20px"><c:out value="${exam.title}"/></h3>
                <div class="big-wrapper">
                    <form class="file-query-container" method="get">
                        <div class="file-filter-container" id="filter-container" style="display: flex">
                            <label for="filter-by" class="file-select-label" style="margin-bottom: 4px">
                                <spring:message code="teacher.correct.exam.filter-by"/>
                            </label>
                            <select name="filter-by" id="filter-by" class="file-select">
                                <option value="all" <c:if test="${filterBy == 'all'}">selected</c:if>>
                                    <spring:message code="teacher.correct.exam.filter.all"/>
                                </option>
                                <option value="corrected" <c:if test="${filterBy == 'corrected'}">selected</c:if>>
                                    <spring:message code="teacher.correct.exam.filter.corrected"/>
                                </option>
                                <option value="not corrected" <c:if test="${filterBy == 'not corrected'}">selected</c:if>>
                                    <spring:message code="teacher.correct.exam.filter.not-corrected"/>
                                </option>
                            </select>
                            <button class="form-button" style="align-self: end">
                                <spring:message code="teacher.correct.exam.filter"/>
                            </button>
                        </div>
                    </form>
                    <h4 class="section-heading" style="margin-left:10px" ><spring:message code="teacher.correct.exam.average" arguments="${average}" htmlEscape="true"/></h4>
                    <c:if test="${answers.size() == 0}">
                        <spring:message code="teacher.correct.exam.none"/>
                    </c:if>
                    <c:forEach var="answer" items="${answers}">
                        <c:set var="answer" value="${answer}" scope="request"/>
                        <c:set var="dateTimeFormatter" value="${dateTimeFormatter}" scope="request"/>
                        <jsp:include page="../components/student-exam-unit.jsp">
                            <jsp:param name="examId" value="${exam.examId}"/>
                            <jsp:param name="isCorrected" value="${answer.score != null}"/>
                        </jsp:include>
                    </c:forEach>
                </div>
                <div class="pagination-wrapper" style="align-self: center">
                    <c:if test="${currentPage > 1}">
                        <a href="<c:url value="/course/${course.courseId}/exam/${exam.examId}?page=${currentPage-1}&pageSize=${pageSize}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="<spring:message code="img.alt.previous.page" />"
                                 class="pagination-arrow x-rotated small-icon">
                        </a>
                    </c:if>
                    <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}"/>
                    <c:if test="${currentPage < maxPage}">
                        <a href="<c:url
                        value="/course/${course.courseId}/exam/${exam.examId}?page=${currentPage+1}&pageSize=${pageSize}&filter-by=${filterBy}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="<spring:message code="img.alt.next.page" />" class="pagination-arrow small-icon">
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
