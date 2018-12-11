<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>SuperHero Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>SuperHero Sightings</h1>
            <hr/>

            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/superHero/displayHerosPage">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/displayLocationsPage">Location</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/displayOrganizationsPage">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sighting/displaySightingPage">Sightings</a></li>
                </ul>    
            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>

            <div class="row">
                <div class="col-md-10">
                    <h2>Recent Sightings</h2>
                    <table id="SightingTable" class="table table-hover">
                        <tr>
                            <th width="30%">Name</th>
                            <th width="30%">Location Name</th>
                            <th width="30%">Address</th>
                            <th width="30%">Latitude</th>
                            <th width="30%">Longitude</th>
                            <th width="30%">Sighting Date</th>
                            <th width="30%">Sighting Time</th>
                        </tr>
                        <c:forEach var="currentSighting" items="${tenRecentSightingsList}">
                            <tr>
                                <td>
                                    <c:out value="${currentSighting.superHero.heroName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.location.locationName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.location.address}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.location.latitude}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.location.longitude}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.sightingDate}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.sightingTime}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> 
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>