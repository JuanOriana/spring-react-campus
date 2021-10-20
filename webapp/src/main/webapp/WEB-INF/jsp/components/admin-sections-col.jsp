<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<body>
<div class="course-sections-col" style="border-top-right-radius: 12px; width:${param.small?'210px':'300px'}">
    <h3 class="course-sections-col-title">
        <spring:message code="admin.page.header" />
    </h3>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/user/new"/>">
        &rsaquo; <spring:message code="admin.button.create.user" /></a> </p>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/course/new"/>">
        &rsaquo; <spring:message code="admin.button.create.course" /></a> </p>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/course/select"/>">
        &rsaquo; <spring:message code="admin.button.add.user.to.course" /></a> </p>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/course/all"/>">
        &rsaquo; <spring:message code="admin.button.all.courses" /></a> </p>
</div>
</body>
</html>
