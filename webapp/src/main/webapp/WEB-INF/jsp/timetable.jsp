<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Timetable</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <link href="<c:url value = "${page.Context.request.contextPath}/resources/css/style.css" />" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap"
          rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp">
    <jsp:param name="navItem" value="${3}"/>
</jsp:include>
<div class="page-container">
    <h2 class="section-heading">Mis Horarios</h2>
    <div class="tab">
        <table class="timetable">
            <tr class="days">
                <th></th>
                <c:forEach items="${days}" var="day">
                    <th>${day}</th>
                </c:forEach>
            </tr>
            <tr>
                <td class="time">8.00</td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [JH1]</td>
                <td class="active-time-td" data-tooltip="Computer Graphics">CS426 [CS1]</td>
                <td></td>
                <td></td>
                <td>- </td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">9.00</td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [JH1]</td>
                <td class="active-time-td" data-tooltip="Computer Graphics">CS426 [CS1]</td>
                <td></td>
                <td></td>
                <td>- </td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">10.00</td>
                <td></td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [Lab]</td>
                <td class="active-time-td" data-tooltip="Multimedia Production & Management">MD352 [Kairos]</td>
                <td></td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">11.00</td>
                <td></td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [Lab]</td>
                <td class="active-time-td" data-tooltip="Multimedia Production & Management">MD352 [Kairos]</td>
                <td class="active-time-td" data-tooltip="Operating Systems">CS240 [CH]</td>
                <td>- </td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">12.00</td>
                <td></td>
                <td class="active-time-td" data-tooltip="Media & Globalisation">MD303 [CS2]</td>
                <td class="active-time-td" data-tooltip="Special Topic: Multiculturalism & Nationalism">MD313 [Iontas]</td>
                <td></td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">13.00</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">14.00</td>
                <td></td>
                <td></td>
                <td class="active-time-td" data-tooltip="Computer Graphics">CS426 [CS2]</td>
                <td class="active-time-td" data-tooltip="Operating Systems">CS240 [TH1]</td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">15.00</td>
                <td></td>
                <td></td>
                <td></td>
                <td class="active-time-td" data-tooltip="Operating Systems">CS240 [Lab]</td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">16.00</td>
                <td></td>
                <td></td>
                <td></td>
                <td class="active-time-td" data-tooltip="Operating Systems">CS240 [Lab]</td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">17.00</td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [TH1]</td>
                <td></td>
                <td></td>
                <td></td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">18.00</td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [JH1]</td>
                <td class="active-time-td" data-tooltip="Computer Graphics">CS426 [CS1]</td>
                <td></td>
                <td></td>
                <td>- </td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">19.00</td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [JH1]</td>
                <td class="active-time-td" data-tooltip="Computer Graphics">CS426 [CS1]</td>
                <td></td>
                <td></td>
                <td>- </td>
                <td>-</td>
            </tr>
            <tr>
                <td class="time">20.00</td>
                <td class="active-time-td" data-tooltip="Software Engineering & Software Process">CS335 [JH1]</td>
                <td class="active-time-td" data-tooltip="Computer Graphics">CS426 [CS1]</td>
                <td></td>
                <td></td>
                <td>- </td>
                <td>-</td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>