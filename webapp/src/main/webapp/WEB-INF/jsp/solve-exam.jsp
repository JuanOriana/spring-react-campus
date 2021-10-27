<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp"/>
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
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> Nombre del examen</h3>
                <div class="big-wrapper">
                    <h3 class="form-label">Esta es tu consigna:</h3>
                    <p>TEXTO ARBITRARIO VA ACA!!!!</p>
<%--                    ACA SE INCLUYE EL ARCHIVO--%>
                    <form:form modelAttribute="solveExamForm" method="post" enctype="multipart/form-data"
                               acceptCharset="utf-8" cssStyle="margin: 30px 0; display: flex; padding:10px;
                               flex-direction: column; border: 2px solid #2EC4B6; border-radius:12px">
                        <form:label path="exam" class="form-label" cssStyle="margin: 0 0 5px 0">
                            Subir solucion
                        </form:label>
                        <form:input type="file" path="exam" accept="application/pdf, application/msword" cssStyle="font-size: 18px;" />
                        <form:errors path="exam" element="p" cssStyle="color:red;margin-left: 15px"/>
                        <div style="display: flex; margin-top:5px; justify-content: center">
                            <button  class="form-button" style="margin-right:15px; background: #a80011";>Cancelar envio</button>
                            <button  class="form-button">Enviar</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>


