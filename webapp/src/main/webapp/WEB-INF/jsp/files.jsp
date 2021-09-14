<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Material</title>
    <c:import url="config/generalHead.jsp"/>
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
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="navItem" value="${3}"/>
    </jsp:include>
    <div class="page-container">
        <h2 class="section-heading">Mi Material</h2>
        <div class="big-wrapper">
            <form action="" class="file-query-container">
                <div style="display: flex; align-items: center; margin-bottom: 10px">
                    <input class="form-input"
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
                    <div style="display: flex; flex-direction: column">
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
                            <input class="file-checkbox" type="checkbox" id="${extension.fileExtensionId}" name="extension-type"
                                   value="${extension.fileExtensionId}" onclick="unToggle('extension-all')">
                            <label class="file-checkbox-label" for="${extension.fileExtensionId}">
                                <c:out value="${extension.fileExtension}"/>
                            </label>
                        </span>
                        </c:forEach>
                    </div>

                    <div style="display: flex; flex-direction: column">
                        <label class="file-select-label">Categoria</label>
                        <span>
                            <input class="file-checkbox" type="checkbox" id="category-all"
                                   name="category-type" value="all" onclick="toggleAll(this)">
                            <label class="file-checkbox-label" for="category-all">todos</label>
                        </span>
                        <c:forEach var="category" items="${categories}">
                        <span>
                            <input class="file-checkbox" type="checkbox" id="category-${category.categoryId}" name="category-type"
                                   value="${category.categoryId}" onclick="unToggle('category-all')">
                            <label class="file-checkbox-label" for="category-${category.categoryId}">
                                <c:out value="${category.categoryName}"/>
                            </label>
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
                        <a href="<c:url value="/course/${file.course.courseId}"/>" class="styleless-anchor">
                            <p class="file-name"><c:out value="${file.course.subject.name}"/></p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>

