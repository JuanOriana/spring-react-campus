<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title>Campus - Todos los cursos</title>
    <c:import url="../config/generalHead.jsp"/>
    <link href="<c:url value = "/resources/css/course-table.css" />" rel="stylesheet" >
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp"/>
    <div class="page-container">
        <h2 class="section-heading">Todos los cursos del ${year}/${quarter}Q</h2>
        <form method="get">
            <label for="year">Anio</label>
            <select name="year" id="year" >
                <option value="2021" <c:if test="${year == 2021}">selected</c:if>>2021</option>
                <option value="2020"<c:if test="${year == 2020}">selected</c:if>>2020</option>
            </select>
            Cuatrimestre
            <label>
                <input type="radio" value="1" name="quarter" <c:if test="${quarter == 1}">checked</c:if>>
                <span>1</span>
            </label>
            <label>
                <input type="radio" value="2" name="quarter" <c:if test="${quarter == 2}">checked</c:if>>
                <span>2</span>
            </label>
            <button>Buscar</button>
        </form>
        <table class="course-table">
            <tr class="course-table-header">
                <th>Codigo</th>
                <th style="width: 300px">Nombre</th>
                <th>Comision</th>
            </tr>
            <c:forEach var="course" items="${courses}">
                <tr>
                    <td><c:out value="${course.subject.code}"/> </td>
                    <td><c:out value="${course.subject.name}"/></td>
                    <td><c:out value="${course.board}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>

</body>
</html>
