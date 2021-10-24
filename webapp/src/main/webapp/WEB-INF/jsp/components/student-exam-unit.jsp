<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>

<body>
<div class="file-unit" id="file-${requestScope.exam.examId}">
    <div style="display: flex; align-items: center">
        <a href="<c:url value="/exams/${requestScope.exam.examId}"/>" class="styleless-anchor"
           style="display: flex;margin-left: 10px; align-items: center">
            <img src="<c:url value="/resources/images/test.png"/>"
                 class="file-img" alt="${requestScope.exam.name}"/>
            <p class="file-name" style="padding-right: 15px; margin-right: 5; border-right: 3px solid white">
                Nombre del alumno
            </p>
        </a>
        <p class="file-name">
            Entregado a las:
        </p>
    </div>
    <div style="display: flex; align-items: center">
            <img src="<c:url value="/resources/images/check.png"/>"
                 alt="delete" class="medium-icon" onclick="deleteById(${requestScope.exam.examId})">
    </div>
</div>
</body>
</html>

