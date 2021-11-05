<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
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

        function clearFilters(){
            const categoryCheckboxes = document.getElementsByName("category-type");
            const extensionCheckboxes = document.getElementsByName("extension-type");
            for(let i=0, n=categoryCheckboxes.length;i<n;i++) {
                categoryCheckboxes[i].checked = false;
            }
            for(let i=0, n=extensionCheckboxes.length;i<n;i++) {
                extensionCheckboxes[i].checked = false;
            }
            const query = document.getElementById("query");
            query.value = ""
        }
    </script>
</head>
<body>
<form action="" class="file-query-container">
    <div style="display: flex; align-items: center; margin-bottom: 10px">
        <input class="form-input" name="query" id="query" style="width: 70%; height: 30px; border-top-right-radius: 0;
        border-bottom-right-radius: 0; border:none; margin: 0" value="${param.query}">
        <button class="form-button" style="height: 100%; margin:0; width: 120px;border-top-left-radius: 0;
        border-bottom-left-radius: 0">
            <spring:message code="file.search.button" />
        </button>
        <img src="<c:url value="/resources/images/outline-arrow.png"/>"
             class="pagination-arrow"  style="transform: rotate(90deg); margin-left: 10px"
             onclick="toggleFilters()" alt="<spring:message code="file.search.img.alt.toggle.filters" />" id="filter-toggle">
    </div>
    <div class="file-filter-container" id="filter-container" style="display: none;">
        <div style="display: flex;justify-content: space-between;">
        <div style="display: flex; flex-direction: column;">
            <label for="order-property" class="file-select-label"><spring:message code="file.search.by" /></label>
            <select name="order-property" id="order-property" class="file-select">
                <option value="date" <c:if test="${param.orderProperty == 'date'}">selected</c:if>>
                    <spring:message code="file.search.order.by.date" htmlEscape="true"/>
                </option>
                <option value="name" <c:if test="${param.orderProperty == 'name'}">selected</c:if>>
                    <spring:message code="file.search.order.by.name" htmlEscape="true"/>
                </option>
                <option value="downloads" <c:if test="${param.orderProperty == 'downloads'}">selected</c:if>>
                    <spring:message code="file.search.order.by.downloads" htmlEscape="true"/>
                </option>
            </select>
            <label for="order-direction" class="file-select-label"><spring:message code="file.search.order" /></label>
            <select name="order-direction" id="order-direction" class="file-select">
                <option value="asc" <c:if test="${param.orderDirection == 'asc'}">selected</c:if>>
                    <spring:message code="file.search.order.asc" htmlEscape="true"/>
                </option>
                <option value="desc" <c:if test="${param.orderDirection == 'desc'}">selected</c:if>>
                    <spring:message code="file.search.order.desc" htmlEscape="true"/>
                </option>
            </select>
        </div>


        <div style="display: flex; flex-direction: column;">
            ${param.extensions}
            <label class="file-select-label"><spring:message code="file.search.type"/></label>
            <span>
                <input class="file-checkbox" type="checkbox" id="extension-all" name="extension-type"
                       value="${0}" onclick="toggleAll(this)"
                       <c:if test="${requestScope.extensionType.equals(requestScope.extensions)}">checked</c:if>>
                <label class="file-checkbox-label" for="extension-all"><spring:message code="file.search.type.all" /></label>
            </span>
            <c:forEach var="extension" items="${requestScope.extensions}" begin="1">
                <span>
                    <input class="file-checkbox" type="checkbox" id="extension-${extension.fileExtensionId}" name="extension-type"
                           value="${extension.fileExtensionId}" onclick="unToggle('extension-all')"
                           <c:if test="${requestScope.extensionType.contains(extension.fileExtensionId)}">checked</c:if>>
                    <label class="file-checkbox-label" for="extension-${extension.fileExtensionId}">
                        <spring:message code="file.search.type.name" htmlEscape="true" arguments="${extension.fileExtensionName}"/>
                    </label>
                </span>
            </c:forEach>
            <span>
                <input class="file-checkbox" type="checkbox" id="extension-${requestScope.extensions[0].fileExtensionId}" name="extension-type"
                       value="${requestScope.extensions[0].fileExtensionId}" onclick="unToggle('extension-all')"
                       <c:if test="${requestScope.extensionType.contains(requestScope.extensions[0].fileExtensionId)}">
                       checked</c:if>>
                <label class="file-checkbox-label" for="extension-${requestScope.extensions[0].fileExtensionId}">
                    <spring:message code="file.search.type.other" />
                </label>
            </span>
        </div>

        <div style="display: flex; flex-direction: column; ">
            <label class="file-select-label"><spring:message code="file.search.category"/></label>
            <span>
                <input class="file-checkbox" type="checkbox" id="category-all" name="category-type"
                       value="${0}" onclick="toggleAll(this)"
                       <c:if test="${requestScope.categoryType.equals(requestScope.categories)}">checked</c:if>>
                <label class="file-checkbox-label" for="category-all"><spring:message code="file.search.category.all"/></label>
            </span>
            <c:forEach var="category" items="${requestScope.categories}">
                <span>
                    <input class="file-checkbox" type="checkbox" id="category-${category.categoryId}" name="category-type"
                           value="${category.categoryId}" onclick="unToggle('category-all')"
                           <c:if test="${requestScope.categoryType.contains(category.categoryId)}">checked</c:if>>
                    <label class="file-checkbox-label" for="category-${category.categoryId}">
                        <spring:message code="category.${category.categoryName}" htmlEscape="true"/>
                    </label>
                </span>
            </c:forEach>
        </div>
        </div>
        <button type="button" class="form-button" style="align-self: end" onclick="clearFilters()"><spring:message code="file.search.button.clear.filters"/></button>
    </div>

<%--    TODO: ver el tema de "Practica" repetido (es por el indice 0 repetido) --%>
    <c:if test="${requestScope.filteredCategories.size() > 0 || requestScope.filteredExtensions.size() > 0}">
        <div>
            <span class="file-checkbox-label"><spring:message code="file.search.filtered.by" htmlEscape="true"/></span>
            <span> </span>
            <span class="file-checkbox-label"><spring:message code="file.search.filtered.by.categories" htmlEscape="true"/></span>
            <span> </span>
            <c:forEach var="category" items="${requestScope.filteredCategories}">
                <span class="file-checkbox-label">
                    <spring:message code="category.${category.categoryName}" htmlEscape="true"/>
                </span>
                <span> </span>
            </c:forEach>
            <span class="file-checkbox-label">- <spring:message code="file.search.filtered.by.extensions" htmlEscape="true"/></span>
            <span> </span>
            <c:forEach var="extension" items="${requestScope.filteredExtensions}">
                <span class="file-checkbox-label">
                    <c:out value = "${extension.getFileExtensionName()}"/>
                </span>
                <span> </span>
            </c:forEach>
        </div>
    </c:if>

</form>
</body>
</html>
