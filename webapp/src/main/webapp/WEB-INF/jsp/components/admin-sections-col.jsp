<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<body>
<div class="course-sections-col" style="border-top-right-radius: 12px; width:${param.small?'210px':'300px'}">
    <h3 class="course-sections-col-title">
        Centro de administración de Campus
    </h3>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/user/new"/>">
        &rsaquo; Crear nuevo usuario</a> </p>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/course/new"/>">
        &rsaquo; Crear nuevo curso</a> </p>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/course/select"/>">
        &rsaquo; Agregar usuario a curso</a> </p>
    <p class="course-sections-item"><a class="styleless-anchor" href="<c:url value ="/admin/course/all"/>">
        &rsaquo; Ver todos los cursos</a> </p>
</div>
</body>
</html>
