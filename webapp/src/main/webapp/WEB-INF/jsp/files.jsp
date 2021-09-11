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
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> Material </h3>
                <div class="big-wrapper">
                    <form class="file-query-container">
                        <div style="display: flex; flex-direction: column; height: 80px;">
                            <label for="order-class" class="file-select-label">Buscar por</label>
                            <select name="order-class" id="order-class" class="file-select">
                                <option>Fecha de subida</option>
                                <option>Nombre</option>
                            </select>
                            <label for="order-by" class="file-select-label">De forma</label>
                            <select name="order-by" id="order-by" class="file-select">
                                <option>Ascendente</option>
                                <option>Descendente</option>
                            </select>
                        </div>
                        <div style="display: flex; flex-direction: column; height: 80px;">
                            <label class="file-select-label">Tipo de archivo</label>
                            <span>
                                <input type="checkbox" id="all" name="all" value="all">
                                <label for="pdf">todos</label>
                            </span>
                            <span>
                                <input type="checkbox" id="pdf" name="pdf" value="pdf">
                                <label for="pdf">pdf</label>
                            </span>
                            <span>
                                <input type="checkbox" id="csv" name="csv" value="csv">
                                <label for="csv">csv</label>
                            </span>
                            <span>
                                <input type="checkbox" id="other" name="other" value="other">
                                <label for="other">otro</label>
                            </span>
                        </div>
                    </form>
                    <div class="file-grid">
                        <div class="file-unit">
                            <img src="<c:url value="${page.Context.request.contextPath}/resources/images/file-img.png"/>"
                            class="file-img" alt="archivo.txt"/>
                            <p class="file-name">archivo.txt</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
