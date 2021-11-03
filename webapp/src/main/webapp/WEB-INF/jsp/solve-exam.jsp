<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/general-head.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        function startTimer(millis,courseId) {
            let x = setInterval(function () {

                // Get today's date and time
                const now = new Date().getTime();
                // Find the distance between now and the count down date
                const delta = millis - now;
                // Time calculations for days, hours, minutes and seconds
                const days = Math.floor(delta / (1000 * 60 * 60 * 24));
                const hours = Math.floor((delta % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((delta % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((delta % (1000 * 60)) / 1000);
                // Display the result in the element with id="demo"
                document.getElementById("time-left").innerHTML = days + "d " + hours + "h "
                    + minutes + "m " + seconds + "s ";

                // If the count down is finished, write some text
                if (delta < 3000) {
                    clearInterval(x);
                    $.ajax({
                        url: '${pageContext.request.contextPath}/course/${course.courseId}/exam/${examId}',
                        type: 'GET',
                        success: function (result) {
                            console.log("success")
                        }
                    });
                    document.getElementById("time-left").innerHTML = "FIN";
                }
            }, 1000);
        }
        window.onload = function () {
            startTimer(${millisToEnd},${courseId});
        }
    </script>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp"/>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="components/course-sections-col.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
                <jsp:param name="year" value="${course.year}"/>
                <jsp:param name="quarter" value="${course.quarter}"/>
                <jsp:param name="code" value="${course.subject.code}"/>
                <jsp:param name="board" value="${course.board}"/>
                <jsp:param name="itemId" value="${4}"/>
            </jsp:include>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <c:out value="${exam.title}"/></h3>
                <div class="big-wrapper">
                    <h3 style="align-self: center">Tiempo restante: <span id="time-left"></span> </h3>
                    <h3 class="form-label"><spring:message code="solve.exam.description" htmlEscape="true"/></h3>
                    <p style="margin-left:30px; margin-top:10px; margin-bottom:10px;"><c:out value="${exam.description}"/></p>
                    <c:set var="file" value="${exam.examFile}" scope="request"/>
                    <jsp:include page="components/file-unit.jsp">
                        <jsp:param name="isMinimal" value="${true}"/>
                    </jsp:include>
                    <form:form modelAttribute="solveExamForm" method="post" enctype="multipart/form-data"
                               acceptCharset="utf-8" cssStyle="margin: 30px 0; display: flex; padding:10px;
                               flex-direction: column; border: 2px solid #2EC4B6; border-radius:12px">
                        <form:label path="exam" class="form-label" cssStyle="margin: 0 0 5px 0">
                            <spring:message code="solve.exam.upload.solution" htmlEscape="true"/>
                        </form:label>
                        <form:input type="file" path="exam" accept="application/pdf, application/msword" cssStyle="font-size: 18px;" />
                        <form:errors path="exam" element="p" cssStyle="color:red;margin-left: 15px"/>
                        <div style="display: flex; margin-top:5px; justify-content: center">
                            <a class="styleless-anchor form-button" style="margin-right:15px; background: #a80011; text-align: center";
                            href="/course/${course.courseId}/exams">
                                <spring:message code="solve.exam.cancel.submission" htmlEscape="true"/>
                            </a>
                            <button  class="form-button"><spring:message code="solve.exam.submit" htmlEscape="true"/></button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>


