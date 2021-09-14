<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - ${course.subject.name}</title>
    <c:import url="../config/generalHead.jsp"/>
    <script>
        function toggleAll(source) {
            const checkboxes = document.getElementsByName(source.name);
            for(let i=0, n=checkboxes.length;i<n;i++) {
                checkboxes[i].checked = source.checked;
            }
        }

        function unToggle(id){
            const checkbox = document.getElementById(id);
            checkbox.checked = false;
        }

        function toggleFilters(){
            const filters = document.getElementById("filter-container");
            const toggler = document.getElementById("filter-toggle");
            if (filters.style.display === "none") {
                filters.style.display = "flex";
                toggler.style.transform="rotate(-90deg)"
            } else {
                filters.style.display = "none";
                toggler.style.transform="rotate(90deg)"
            }
        }
    </script>
</head>
<body>
<div class="page-organizer">
    <%@ include file="../components/navbar.jsp" %>
    <h2 class="course-section-name">${course.subject.name}</h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="../components/courseSectionsCol.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
            </jsp:include>
            <c:url value="/teacher-course/${courseId}/files" var="postUrl"/>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> Material </h3>
                <form method="post" action="${postUrl}" enctype="multipart/form-data" class="form-wrapper reduced">
                    <h1 class="announcement-title" style="color:#176961; align-self:center">Subir nuevo archivo</h1>
                    <label for="file" class="form-label">Archivo</label>
                    <input id="file" name="file" type="file" class="form-input" style="font-size: 26px">
                    <label for="category" class="form-label">Categoria</label>
                    <select id="category" name="category" class="form-input" style="font-size: 26px">
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}"><c:out value="${category.categoryName}"/></option>
                        </c:forEach>
                    </select>
                    <button class="form-button">Publicar</button>
                </form>
                <div class="separator reduced">.</div>
                <div class="big-wrapper">
                    <form action="" class="file-query-container">
                        <div style="display: flex; align-items: center; margin-bottom: 10px">
                            <input class="form-input" name="query"
                                   style="width: 70%; height: 30px; border-top-right-radius: 0;
                                   border-bottom-right-radius: 0; border:none">
                            <button class="form-button"
                                    style="height: 30px; margin:0; width: 120px;
                                    border-top-left-radius: 0; border-bottom-left-radius: 0">
                                Buscar
                            </button>
                            <img src="<c:url value="${page.Context.request.contextPath}/resources/images/page-arrow.png"/>"
                                 class="pagination-arrow"  style="transform: rotate(90deg); margin-left: 10px"
                                 onclick="toggleFilters()" alt="toggle filters" id="filter-toggle">
                        </div>
                        <div class="file-filter-container" id="filter-container" style="display: none">
                            <div style="display: flex; flex-direction: column;">
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


                            <div style="display: flex; flex-direction: column;">
                                <label class="file-select-label">Tipo de archivo</label>
                                <span>
                                    <input class="file-checkbox" type="checkbox" id="extension-all" name="extension-type"
                                           value="all" onclick="toggleAll(this)">
                                    <label class="file-checkbox-label" for="extension-all">todos</label>
                                </span>
                                <c:forEach var="extension" items="${extensions}">
                                    <span>
                                        <input class="file-checkbox" type="checkbox" id="extension-${extension.fileExtensionId}" name="extension-type"
                                               value="${extension.fileExtensionId}" onclick="unToggle('extension-all')">
                                        <label class="file-checkbox-label" for="extension-${extension.fileExtensionId}">
                                            <c:out value="${extension.fileExtension}"/>
                                        </label>
                                    </span>
                                </c:forEach>
                            </div>

                            <div style="display: flex; flex-direction: column; ">
                                <label class="file-select-label">Categoria</label>
                                <span>
                                    <input class="file-checkbox" type="checkbox" id="category-all" name="category-type"
                                           value="all" onclick="toggleAll(this)">
                                    <label class="file-checkbox-label" for="category-all">todos</label>
                                </span>
                                <c:forEach var="category" items="${categories}">
                                    <span>
                                        <input class="file-checkbox" type="checkbox" id="category-${category.categoryId}" name="category-type"
                                               value="${category.categoryId}" onclick="unToggle('category-all')">
                                        <label class="file-checkbox-label" for="category-${category.categoryId}"><c:out value="${category.categoryName}"/></label>
                                    </span>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                    <div class="file-grid">
                        <c:forEach var="file" items="${files}">
                            <div class="file-unit">
                                <a href="<c:url value="/savefile/${file.fileId}"/>" class="styleless-anchor"
                                   style="display: flex;margin-left: 10px; align-items: center">
                                    <img src="<c:url value="${page.Context.request.contextPath}/resources/images/extensions/${file.extension.fileExtension}.png"/>"
                                         class="file-img" alt="${file.name}"/>
                                <p class="file-name"><c:out value=" ${file.name}"/></p>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
