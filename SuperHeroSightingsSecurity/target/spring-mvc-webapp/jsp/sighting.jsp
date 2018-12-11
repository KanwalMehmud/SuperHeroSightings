<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superHero/displayHerosPage">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/displayLocationsPage">Location</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/displayOrganizationsPage">Organization</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/sighting/displaySightingPage">Sightings</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">User Admin</a></li>                        
                        </sec:authorize>
                </ul>    
            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>

            <div class="row">
                <!-- 
                    col to hold the summary table
                -->
                <div class="col-md-8">
                    <h2>Sightings</h2>
                    <table id="SightingTable" class="table table-hover">
                        <tr>
                            <th width="30%">Name</th>
                            <th width="25%">Location Name</th>
                            <th width="30%">Address</th>
                            <th width="30%">Latitude</th>
                            <th width="30%">Longitude</th>
                            <th width="40%">Sighting Date</th>
                            <th width="30%">Sighting Time</th>

                        </tr>
                        <c:forEach var="currentSighting" items="${sightingsList}">
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
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteSighting?sightingsId=${currentSighting.sightingsId}">
                                            Delete
                                        </a>
                                    </sec:authorize>

                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> 

                <div class="col-md-1">                    
                </div>
                <div class="col-md-3">
                    <h2>Add New Sighting</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createSighting">
                        <div class="form-group">
                            <label for="add-hero" class="col-md-4 control-label">Hero/Villain:</label>
                            <select name="superHero.superHeroId">
                                <c:forEach var="currentHero" items="${herosList}">
                                    <option value="${currentHero.superHeroId}"/>
                                    <c:out value="${currentHero.heroName}" />                                
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="add-location" class="col-md-4 control-label">Location:</label>
                            <select name="location.locationId">
                                <c:forEach var="currentLocation" items="${locationList}">
                                    <option value="${currentLocation.locationId}"/>
                                    <c:out value="${currentLocation.locationName}" />                                
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="add-date" class="col-md-4 control-label">Date:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="sightingDateString" placeholder="Sight Date:" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-time" class="col-md-4 control-label">Time:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="sightingTime" placeholder="Sight Time:" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Sighting"/>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
