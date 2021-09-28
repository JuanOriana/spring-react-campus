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
    </script>
</head>
<body>
<form action="" class="file-query-container">
    <div style="display: flex; align-items: center; margin-bottom: 10px">
        <input class="form-input" name="query" style="width: 70%; height: 30px; border-top-right-radius: 0;
        border-bottom-right-radius: 0; border:none; margin: 0" value="${param.query}">
        <button class="form-button" style="height: 100%; margin:0; width: 120px;border-top-left-radius: 0;
        border-bottom-left-radius: 0">
            <spring:message code="file.search.button" />
        </button>
        <img src="<c:url value="/resources/images/outline-arrow.png"/>"
             class="pagination-arrow"  style="transform: rotate(90deg); margin-left: 10px"
             onclick="toggleFilters()" alt="toggle filters" id="filter-toggle">
    </div>
    <div class="file-filter-container" id="filter-container" style="display: none">
        <div style="display: flex; flex-direction: column;">
            <label for="order-property" class="file-select-label"><spring:message code="file.search.by" /></label>
            <select name="order-property" id="order-property" class="file-select">
                <option value="date" <c:if test="${param.orderClass == 'date'}">selected</c:if>>
                    <spring:message code="file.search.order.by.date" htmlEscape="true"/>
                </option>
                <option value="name" <c:if test="${param.orderClass == 'name'}">selected</c:if>>
                    <spring:message code="file.search.order.by.name" htmlEscape="true"/>
                </option>
                <option value="downloads" <c:if test="${param.orderClass == 'downloads'}">selected</c:if>>
                    <spring:message code="file.search.order.by.downloads" htmlEscape="true"/>
                </option>
            </select>
            <label for="order-direction" class="file-select-label"><spring:message code="file.search.order" /></label>
            <select name="order-direction" id="order-direction" class="file-select">
                <option value="asc" <c:if test="${param.orderBy == 'asc'}">selected</c:if>>
                    <spring:message code="file.search.order.asc" htmlEscape="true"/>
                </option>
                <option value="desc" <c:if test="${param.orderBy == 'desc'}">selected</c:if>>
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
            <c:forEach var="extension" items="${requestScope.extensions}">
                <span>
                    <input class="file-checkbox" type="checkbox" id="extension-${extension.fileExtensionId}" name="extension-type"
                           value="${extension.fileExtensionId}" onclick="unToggle('extension-all')"
                           <c:if test="${requestScope.extensionType.contains(extension.fileExtensionId)}">checked</c:if>>
                    <label class="file-checkbox-label" for="extension-${extension.fileExtensionId}">
                        <c:choose>
                            <c:when test="${extension.fileExtensionName.equals('other')}">
                                <spring:message code="file.search.type.other" />
                            </c:when>
                            <c:otherwise>
                                <spring:message code="file.search.type.name" htmlEscape="true" arguments="${extension.fileExtensionName}"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </span>
            </c:forEach>
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
</form>
</body>
</html>
