<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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
        border-bottom-right-radius: 0; border:none" value="${param.query}">
        <button class="form-button" style="height: 30px; margin:0; width: 120px;border-top-left-radius: 0;
        border-bottom-left-radius: 0">
            <spring:message code="file.search.button" htmlEscape="true"/>
        </button>
        <img src="<c:url value="${pageContext.request.contextPath}/resources/images/page-arrow.png"/>"
             class="pagination-arrow"  style="transform: rotate(90deg); margin-left: 10px"
             onclick="toggleFilters()" alt="toggle filters" id="filter-toggle">
    </div>
    <div class="file-filter-container" id="filter-container" style="display: none">
        <div style="display: flex; flex-direction: column;">
            <label for="order-class" class="file-select-label"><spring:message code="file.search.by" htmlEscape="true"/></label>
            <select name="order-class" id="order-class" class="file-select">
                <option value="DATE" <c:if test="${param.orderClass == 'DATE'}">selected</c:if>>
                    <spring:message code="file.search.order.by.date" htmlEscape="true"/>
                </option>
                <option value="NAME" <c:if test="${param.orderClass == 'NAME'}">selected</c:if>>
                    <spring:message code="file.search.order.by.name" htmlEscape="true"/>
                </option>
            </select>
            <label for="order-by" class="file-select-label"><spring:message code="file.search.order" htmlEscape="true"/></label>
            <select name="order-by" id="order-by" class="file-select">
                <option value="ASC" <c:if test="${param.orderBy == 'ASC'}">selected</c:if>>
                    <spring:message code="file.search.order.asc" htmlEscape="true"/>
                </option>
                <option value="DESC" <c:if test="${param.orderBy == 'DESC'}">selected</c:if>>
                    <spring:message code="file.search.order.desc" htmlEscape="true"/>
                </option>
            </select>
        </div>


        <div style="display: flex; flex-direction: column;">
            ${param.extensions}
            <label class="file-select-label"><spring:message code="file.search.type" htmlEscape="true"/></label>
            <span>
                <input class="file-checkbox" type="checkbox" id="extension-all" name="extension-type"
                       value="${0}" onclick="toggleAll(this)"
                       <c:if test="${requestScope.extensionType.equals(requestScope.extensions)}">checked</c:if>>
                <label class="file-checkbox-label" for="extension-all"><spring:message code="file.search.type.all" htmlEscape="true"/></label>
            </span>
            <c:forEach var="extension" items="${requestScope.extensions}">
                <span>
                    <input class="file-checkbox" type="checkbox" id="extension-${extension.fileExtensionId}" name="extension-type"
                           value="${extension.fileExtensionId}" onclick="unToggle('extension-all')"
                           <c:if test="${requestScope.extensionType.contains(extension.fileExtensionId)}">checked</c:if>>
                    <label class="file-checkbox-label" for="extension-${extension.fileExtensionId}">
                        <c:choose>
                            <c:when test="${extension.fileExtension.equals('other')}">
                                <spring:message code="file.search.type.other" htmlEscape="true"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="file.search.type.name" htmlEscape="true" arguments="${extension.fileExtension}"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </span>
            </c:forEach>
        </div>

        <div style="display: flex; flex-direction: column; ">
            <label class="file-select-label"><spring:message code="file.search.category" htmlEscape="true"/></label>
            <span>
                <input class="file-checkbox" type="checkbox" id="category-all" name="category-type"
                       value="${0}" onclick="toggleAll(this)"
                       <c:if test="${requestScope.categoryType.equals(requestScope.categories)}">checked</c:if>>
                <label class="file-checkbox-label" for="category-all"><spring:message code="file.search.category.all" htmlEscape="true"/></label>
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
