<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - ${course.subject.name}</title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
<%@ include file="components/navbar.jsp" %>
<h2 class="course-section-name">${course.subject.name}</h2>
<div class="page-container" style="padding-top: 0">
    <div class="course-page-wrapper">
        <jsp:include page="components/courseSectionsCol.jsp">
            <jsp:param name="courseName" value="${course.subject.name}"/>
            <jsp:param name="courseId" value="${course.courseId}"/>
        </jsp:include>
        <div class="course-data-container">
            <h3 class="section-heading" style="margin: 0 0 20px 20px"> Profesores </h3>
            <div class="professors-wrapper">
                <c:forEach var="teacher" items="${teacherSet}">
                        <div class="professor-unit">
                            <img alt="professor icon" class="professor-icon" src="https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/user.png"/>
                            <div style="display: flex; width:200px; flex-direction: column">
                                <p><c:out value="${teacher.key.name} ${teacher.key.surname}"/></p>
                                <p><c:out value="${teacher.key.email}"/></p>
                            </div>
                            <a class="styleless-anchor" href="mailto:${teacher.key.email}">
                                <img alt="mail icon" class="mail-icon"
                                     src="https://i.pinimg.com/originals/3a/4e/95/3a4e95aa862636d6f22c95fded897f94.jpg"/>
                            </a>
                        </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
