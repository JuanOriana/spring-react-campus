<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Campus - ${course.name}</title>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
        <link href="<c:url value = "${page.Context.request.contextPath}/resources/css/style.css" />" rel="stylesheet" >
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap" rel="stylesheet">

    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <h2 class="course-section-name">${course.name}</h2>
        <div class="page-container" style="padding-top: 0">
            <div class="course-page-wrapper">
                <jsp:include page="courseSectionsCol.jsp">
                    <jsp:param name="courseName" value="${course.name}"/>
                    <jsp:param name="courseId" value="${course.courseId}"/>
                </jsp:include>
                <div class="course-data-container">
                    <h3 class="section-heading" style="margin: 0 0 20px 20px"> Anuncios </h3>
                    <div class="announcement-wrapper">
                        <div class="announcement-header">
                            <h4 class="announcement-title">Anuncio De Prueba</h4>
                            <p style="font-size: 14px">Publicado por: Pollo Oriana</p>
                        </div>
                        <p class="announcement-date">Miércoles 4 de agosto de 2021 19H10' ART</p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum id erat sit amet tellus imperdiet tempor. Nam laoreet erat eros, nec varius velit dapibus pulvinar. Aenean interdum lacus ac urna eleifend, volutpat auctor risus rhoncus. Aenean tempus, massa vitae semper elementum, leo eros lacinia sapien, ac ornare est justo maximus lectus. Etiam turpis mi, condimentum aliquam efficitur ut, dictum vel nibh. Maecenas blandit felis eget eros varius, vitae venenatis turpis commodo. Phasellus vel aliquam nisi. Vestibulum mattis elit dictum, dignissim sapien ut, porta neque. Curabitur tincidunt, metus vitae ultricies eleifend, tortor urna pretium mauris, nec placerat enim libero pharetra nulla. In leo ex, volutpat ac semper sed, sagittis sit amet ipsum. Phasellus turpis ante, auctor nec eleifend a, efficitur nec ante. Nam ultricies quis enim et cursus. Fusce urna arcu, ultrices vel consequat ut, pretium at leo. Mauris at nisl ut ipsum facilisis vestibulum ac quis ligula. Vestibulum vestibulum nec quam ac aliquet. Donec volutpat, risus condimentum suscipit elementum, ex neque laoreet turpis, aliquam aliquet dolor lorem vitae neque.
                    </div>
                    <div class="announcement-wrapper">
                        <div class="announcement-header">
                            <h4 class="announcement-title">Anuncio De Prueba</h4>
                            <p style="font-size: 14px">Publicado por: Pollo Oriana</p>
                        </div>
                        <p class="announcement-date">Miércoles 4 de agosto de 2021 19H10' ART</p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum id erat sit amet tellus imperdiet tempor. Nam laoreet erat eros, nec varius velit dapibus pulvinar. Aenean interdum lacus ac urna eleifend, volutpat auctor risus rhoncus. Aenean tempus, massa vitae semper elementum, leo eros lacinia sapien, ac ornare est justo maximus lectus. Etiam turpis mi, condimentum aliquam efficitur ut, dictum vel nibh. Maecenas blandit felis eget eros varius, vitae venenatis turpis commodo. Phasellus vel aliquam nisi. Vestibulum mattis elit dictum, dignissim sapien ut, porta neque. Curabitur tincidunt, metus vitae ultricies eleifend, tortor urna pretium mauris, nec placerat enim libero pharetra nulla. In leo ex, volutpat ac semper sed, sagittis sit amet ipsum. Phasellus turpis ante, auctor nec eleifend a, efficitur nec ante. Nam ultricies quis enim et cursus. Fusce urna arcu, ultrices vel consequat ut, pretium at leo. Mauris at nisl ut ipsum facilisis vestibulum ac quis ligula. Vestibulum vestibulum nec quam ac aliquet. Donec volutpat, risus condimentum suscipit elementum, ex neque laoreet turpis, aliquam aliquet dolor lorem vitae neque.
                    </div>
                    <div class="announcement-wrapper">
                        <div class="announcement-header">
                            <h4 class="announcement-title">Anuncio De Prueba</h4>
                            <p style="font-size: 14px">Publicado por: Pollo Oriana</p>
                        </div>
                        <p class="announcement-date">Miércoles 4 de agosto de 2021 19H10' ART</p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum id erat sit amet tellus imperdiet tempor. Nam laoreet erat eros, nec varius velit dapibus pulvinar. Aenean interdum lacus ac urna eleifend, volutpat auctor risus rhoncus. Aenean tempus, massa vitae semper elementum, leo eros lacinia sapien, ac ornare est justo maximus lectus. Etiam turpis mi, condimentum aliquam efficitur ut, dictum vel nibh. Maecenas blandit felis eget eros varius, vitae venenatis turpis commodo. Phasellus vel aliquam nisi. Vestibulum mattis elit dictum, dignissim sapien ut, porta neque. Curabitur tincidunt, metus vitae ultricies eleifend, tortor urna pretium mauris, nec placerat enim libero pharetra nulla. In leo ex, volutpat ac semper sed, sagittis sit amet ipsum. Phasellus turpis ante, auctor nec eleifend a, efficitur nec ante. Nam ultricies quis enim et cursus. Fusce urna arcu, ultrices vel consequat ut, pretium at leo. Mauris at nisl ut ipsum facilisis vestibulum ac quis ligula. Vestibulum vestibulum nec quam ac aliquet. Donec volutpat, risus condimentum suscipit elementum, ex neque laoreet turpis, aliquam aliquet dolor lorem vitae neque.
                    </div>
                </div>

            </div>
        </div>
    </body>
</html>
