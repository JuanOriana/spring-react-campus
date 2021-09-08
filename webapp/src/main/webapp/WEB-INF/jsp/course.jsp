<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Campus - <c:out value="${course.subject.name}"/></title>
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
                    <h3 class="section-heading" style="margin: 0 0 20px 20px"> Anuncios </h3>
                    <c:forEach var="announcementItem" items="${announcementList}">
                        <div class="announcement-wrapper reduced">
                            <div class="announcement-header">
                                <h4 class="announcement-title"><c:out value="${announcementItem.title}"/></h4>
                                <p style="font-size: 14px">Publicado por: <c:out value="${announcementItem.author.name}
                                ${announcementItem.author.surname}"/></p>
                            </div>
                            <p class="announcement-date"><c:out value="${announcementItem.date}"/></p>
                                <c:out value="${announcementItem.content}"/>
                        </div>
                    </c:forEach>
                </div>

            </div>
        </div>

    </body>
</html>
