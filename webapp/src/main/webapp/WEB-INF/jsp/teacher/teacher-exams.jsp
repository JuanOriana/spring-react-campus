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
        <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="course-exams.section-heading.title"/> </h3>
        <form:form modelAttribute="createExamForm" class="form-wrapper reduced" method="post" enctype="multipart/form-data" acceptCharset="utf-8">
          <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="teacher.exams.upload.card.title"/></h1>
          <form:label path="title" for="title" class="form-label">
            <spring:message code="teacher.exams.upload.exam.title.field"/>
          </form:label>
          <form:input type="text" path="title" class="form-input" style="font-size: 26px"/>
          <form:errors path="title" element="p" cssClass="error-message"/>
          <form:label path="content" for="content" class="form-label">
            <spring:message code="teacher.exams.upload.exam.instructions.field"/>
          </form:label>
          <form:textarea path="content" class="form-input" style="width: 95%;resize: none" cols="50" rows="10"></form:textarea>
          <form:errors path="content" element="p" cssClass="error-message"/>
          <form:label path="file" for="file" class="form-label"><spring:message code="teacher.exams.upload.exam.attach"/></form:label>
          <form:input path="file" type="file" class="form-input" style="font-size: 26px"/>
          <form:errors path="file" element="p" cssClass="error-message"/>
          <button class="form-button"><spring:message code="teacher.course.button.create.announcement" htmlEscape="true"/></button>
        </form:form>
        <div class="separator reduced">.</div>

        <div class="big-wrapper">
          <h3 style="margin: 10px 0;"><spring:message code="teacher.exams.recent.exams.title"/></h3>
          <c:if test="${exams.size() == 0}">
            No hay examenes aun
          </c:if>
          <c:forEach var="exam" items="${exams}">
            <c:set var="exam" value="${exam}" scope="request"/>
            <jsp:include page="../components/exam-unit.jsp">
            <jsp:param name="isTeacher" value="${true}"/>
            <jsp:param name="userCount" value="${userCount}"/>
          </jsp:include>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>

