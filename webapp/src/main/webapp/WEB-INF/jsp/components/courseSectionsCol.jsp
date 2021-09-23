<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<body>
<div class="course-sections-col">
  <h3 class="course-sections-col-title"><spring:message code="course.sections.col.course.name" htmlEscape="true" arguments="${param.courseName}"/></h3>
  <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/course/${param.courseId}"/>"><spring:message code="course.sections.col.announcements" htmlEscape="true"/></a> </p>
  <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/course/${param.courseId}/teachers"/>"><spring:message code="course.sections.col.teachers" htmlEscape="true"/></a> </p>
  <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/course/${param.courseId}/files"/>"><spring:message code="course.sections.col.files" htmlEscape="true"/></a> </p>
</div>
</body>
</html>