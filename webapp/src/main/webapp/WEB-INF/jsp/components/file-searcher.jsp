<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
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
<form action="" class="file-query-container">
    <div style="display: flex; align-items: center; margin-bottom: 10px">
        <input class="form-input" name="query" style="width: 70%; height: 30px; border-top-right-radius: 0;
        border-bottom-right-radius: 0; border:none; margin: 0" value="${param.query}">
        <button class="form-button" style="height: 30px; margin:0; width: 120px;border-top-left-radius: 0;
        border-bottom-left-radius: 0; padding: 0">
            Buscar
        </button>
        <img src="<c:url value="/resources/images/page-arrow.png"/>"
             class="pagination-arrow"  style="transform: rotate(90deg); margin-left: 10px"
             onclick="toggleFilters()" alt="toggle filters" id="filter-toggle">
    </div>
    <div class="file-filter-container" id="filter-container" style="display: none">
        <div style="display: flex; flex-direction: column;">
            <label for="order-class" class="file-select-label">Buscar por</label>
            <select name="order-class" id="order-class" class="file-select">
                <option value="DATE" <c:if test="${param.orderClass == 'DATE'}">selected</c:if>>
                    Fecha de subida
                </option>
                <option value="NAME" <c:if test="${param.orderClass == 'NAME'}">selected</c:if>>
                    Nombre
                </option>
            </select>
            <label for="order-by" class="file-select-label">De forma</label>
            <select name="order-by" id="order-by" class="file-select">
                <option value="ASC" <c:if test="${param.orderBy == 'ASC'}">selected</c:if>>
                    Ascendente
                </option>
                <option value="DESC" <c:if test="${param.orderBy == 'DESC'}">selected</c:if>>
                    Descendente
                </option>
            </select>
        </div>


        <div style="display: flex; flex-direction: column;">
            ${param.extensions}
            <label class="file-select-label">Tipo de archivo</label>
            <span>
                <input class="file-checkbox" type="checkbox" id="extension-all" name="extension-type"
                       value="${0}" onclick="toggleAll(this)"
                       <c:if test="${requestScope.extensionType.equals(requestScope.extensions)}">checked</c:if>>
                <label class="file-checkbox-label" for="extension-all">todos</label>
            </span>
            <c:forEach var="extension" items="${requestScope.extensions}">
                <span>
                    <input class="file-checkbox" type="checkbox" id="extension-${extension.fileExtensionId}" name="extension-type"
                           value="${extension.fileExtensionId}" onclick="unToggle('extension-all')"
                           <c:if test="${requestScope.extensionType.contains(extension.fileExtensionId)}">checked</c:if>>
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
                       value="${0}" onclick="toggleAll(this)"
                       <c:if test="${requestScope.categoryType.equals(requestScope.categories)}">checked</c:if>>
                <label class="file-checkbox-label" for="category-all">todos</label>
            </span>
            <c:forEach var="category" items="${requestScope.categories}">
                <span>
                    <input class="file-checkbox" type="checkbox" id="category-${category.categoryId}" name="category-type"
                           value="${category.categoryId}" onclick="unToggle('category-all')"
                           <c:if test="${requestScope.categoryType.contains(category.categoryId)}">checked</c:if>>
                    <label class="file-checkbox-label" for="category-${category.categoryId}">
                        <c:out value="${category.categoryName}"/>
                    </label>
                </span>
            </c:forEach>
        </div>
    </div>
</form>
</body>
</html>
