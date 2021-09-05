<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - ${course.subject.name}</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <link href="<c:url value = "${page.Context.request.contextPath}/resources/css/style.css" />" rel="stylesheet" >
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.jsp" %>
<h2 class="course-section-name">${course.subject.name}</h2>
<div class="page-container" style="padding-top: 0">
    <div class="course-page-wrapper">
        <jsp:include page="courseSectionsCol.jsp">
            <jsp:param name="courseName" value="${course.subject.name}"/>
            <jsp:param name="courseId" value="${course.courseId}"/>
        </jsp:include>
        <div class="course-data-container">
            <h3 class="section-heading" style="margin: 0 0 20px 20px"> Profesores </h3>
            <div class="professors-wrapper">
                <div class="professor-unit">
                    <img alt="professor icon" class="professor-icon" src="https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/user.png"/>
                    <div style="display: flex;flex-direction: column">
                        <p>Juan Pablo Oriana</p>
                        <p>joriana@itba.edu.ar</p>
                    </div>
                    <img alt="mail icon" class="mail-icon" src="https://i.pinimg.com/originals/3a/4e/95/3a4e95aa862636d6f22c95fded897f94.jpg"/>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
