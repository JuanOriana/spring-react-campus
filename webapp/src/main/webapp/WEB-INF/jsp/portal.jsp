<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Campus</title>
        <link href="<c:url value = "../../resources/css/style.css" />" rel="stylesheet" >
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap" rel="stylesheet">
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <div class="page-container">
            <h2 class="section-heading">Mis Cursos</h2>
            <div class="courses-container">
                <c:forEach var="courseItem" items="${courseList}">
                    <div class="course">
                        <p class="course-name"><a href="<c:url value="course/${courseItem.subjectId}"/>" class="styleless-anchor"> ${courseItem.name}</a></p>
                        <p class="course-extra-info">2021/2Q</p>
                    </div>
                </c:forEach>

            </div>
        </div>

    </body>
</html>