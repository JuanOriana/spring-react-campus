<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Campussss</title>
        <c:import url="generalHead.jsp"/>
    </head>
    <body>
    <div class="page-organizer">
        <jsp:include page="navbar.jsp">
            <jsp:param name="navItem" value="${1}"/>
        </jsp:include>
        <div class="page-container">
            <h2 class="section-heading">Mis Cursos</h2>
            <div class="courses-container">
                <c:forEach var="courseItem" items="${courseList}">
                    <div class="course">
                        <p class="course-name">
                            <a href="<c:url value="course/${courseItem.subject.subjectId}"/>" class="styleless-anchor">
                                    <c:out value="${courseItem.subject.name}"/>
                            </a>
                        </p>
                        <p class="course-extra-info"><c:out value="${courseItem.year}/${courseItem.quarter}Q"/></p>
                    </div>
                </c:forEach>

            </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </div>
    </body>
</html>