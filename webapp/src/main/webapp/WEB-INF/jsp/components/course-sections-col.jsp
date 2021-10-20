<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<body>
<div class="course-sections-col">
  <h3 class="course-sections-col-title">
    <spring:message code="course.sections.col.course.name" htmlEscape="true" arguments="${param.courseName},${param.year},${param.quarter},${param.code},${param.board}"/>
  </h3>
  <p class="course-sections-item" style="${param.itemId == 1?"color:white":""}">
    <a class="styleless-anchor" href="<c:url value ="/course/${param.courseId}"/>">
      &rsaquo; <spring:message code="course.sections.col.announcements"/>
    </a>
  </p>
  <p class="course-sections-item" style="${param.itemId == 2?"color:white":""}">
    <a class="styleless-anchor" href="<c:url value ="/course/${param.courseId}/teachers"/>">
    &rsaquo; <spring:message code="course.sections.col.teachers" />
    </a>
  </p>
  <p class="course-sections-item" style="${param.itemId == 3?"color:white":""}">
    <a class="styleless-anchor" href="<c:url value ="/course/${param.courseId}/files"/>">
    &rsaquo; <spring:message code="course.sections.col.files"/>
  </a>
  </p>
</div>
</body>
</html>