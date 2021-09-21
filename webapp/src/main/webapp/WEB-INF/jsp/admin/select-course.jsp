<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Campus - Seleccionar Curso</title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="isAdmin" value="${true}"/>
    </jsp:include>
    <div class="page-container">
        <form action="/admin/add-user-to-course" class="form-wrapper reduced" method="get" css="margin: 30px 0">
            <h1 class="announcement-title" style="color:#176961; align-self:center">Seleccionar un curso</h1>
            <label name="courseId" for="courseId" class="form-label">Curso</label>
            <select name="courseId" id="courseId" class="form-input" style="font-size: 26px">
                <c:forEach var="course" items="${courses}">
                    <option value="${course.courseId}">
                        <c:out value="${course.subject.name}[${course.board}]-${course.year}/${course.quarter}Q"/>
                    </option>
                </c:forEach>
            </select>
            <button class=" form-button">Ir a agregar usuarios</button>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
