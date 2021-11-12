<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="../config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp"/>
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
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <c:out value="${exam.title}"/></h3>
                <div class="big-wrapper">
                    <h3 class="form-label"><spring:message code="correct.specific.exam.description" htmlEscape="true"/></h3>
                    <p style="margin-left:30px; margin-top:10px; margin-bottom:10px;"><c:out value="${exam.description}"/></p>
                    <c:set var="file" value="${exam.examFile}" scope="request"/>
                    <jsp:include page="../components/file-unit.jsp">
                        <jsp:param name="isMinimal" value="${true}"/>
                    </jsp:include>
                    <h3 class="form-label"><spring:message code="correct.specific.exam.solution" /></h3>
                    <c:if test="${answer.deliveredDate != null}">
                        <c:set var="file" value="${answer.answerFile}" scope="request"/>
                        <jsp:include page="../components/file-unit.jsp">
                            <jsp:param name="isMinimal" value="${true}"/>
                        </jsp:include>
                    </c:if>
                    <c:if test="${answer.deliveredDate == null}">
                        <spring:message code="correct.specific.exam.not.done" />
                    </c:if>
                    <form:form modelAttribute="answerCorrectionForm" method="post" enctype="multipart/form-data"
                               acceptCharset="utf-8" cssStyle="margin: 30px 0; display: flex; padding:10px;
                               flex-direction: column; border: 2px solid #2EC4B6; border-radius:12px">
                        <form:label path="mark" class="form-label" cssStyle="margin: 0 0 5px 0">
                            <spring:message code="correct.specific.exam.grade" />
                        </form:label>
                        <form:input type="number" path="mark" min="0" max="10" step=".01" cssStyle="font-size: 24px; margin-left:20px; width:80px;padding:3px" />
                        <form:errors path="mark" element="p" cssStyle="color:red;margin-left: 15px"/>
                        <form:label path="comments" for="comments" class="form-label">
                            <spring:message code="correct.specific.exam.comments" />
                        </form:label>
                        <form:textarea path="comments" class="form-input" style="width: 95%;resize: none" cols="50" rows="5"></form:textarea>
                        <form:errors path="comments" element="p" cssClass="error-message"/>
                        <div style="display: flex; margin-top:5px; justify-content: center">
                            <button  class="form-button" style="margin-right:15px; background: #a80011";><spring:message code="correct.specific.exam.cancel.submission" htmlEscape="true"/></button>
                            <button  class="form-button"><spring:message code="correct.specific.exam.submit" htmlEscape="true"/></button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>