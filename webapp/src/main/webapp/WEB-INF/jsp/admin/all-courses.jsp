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
        <form method="get" class="course-table-form">
            <div style="display: flex">
                <div style="display: flex;flex-direction: column">
                    <label for="year" class="form-label">Anio</label>
                    <select name="year" id="year" class="form-input">
                        <c:forEach var="optionYear" items="${allYears}">
                            <option value="${optionYear}" <c:if test="${year == optionYear}">selected</c:if>>
                                    <c:out value="${optionYear}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div style="display: flex;flex-direction: column; align-items: center">
                    <p class="form-label">Cuatrimestre</p>
                    <div style="display: flex; width: 70%; justify-content: space-between">
                        <label class="form-label" style="margin:0">
                            <input type="radio" value="1" name="quarter" <c:if test="${quarter == 1}">checked</c:if>>
                            <span>1</span>
                        </label>
                        <label class="form-label" style="margin:0">
                            <input type="radio" value="2" name="quarter" <c:if test="${quarter == 2}">checked</c:if>>
                            <span>2</span>
                        </label>
                    </div>
                </div>
            </div>
            <button class="form-button">Buscar</button>
        </form>
        <c:if test="${empty courses}">
            <h2 style="margin-top: 20px">No hay cursos para este cuatrimestre</h2>
        </c:if>
        <c:if test="${not empty courses}">
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
        </c:if>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>

</body>
</html>
