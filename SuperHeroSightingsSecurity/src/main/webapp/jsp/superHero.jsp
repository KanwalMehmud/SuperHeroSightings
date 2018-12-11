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
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superHero/displayHerosPage">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/displayLocationsPage">Location</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/displayOrganizationsPage">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sighting/displaySightingPage">Sightings</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">User Admin</a></li>                        
                        </sec:authorize>
                </ul> 
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
                <div class="col-md-6">
                    <h2>Superheros/Supervillains</h2>
                    <table id="HeroTable" class="table table-hover">
                        <tr>
                            <th width="40%">Superhero/Villain Name</th>
                            <th width="30%">Super Power</th>
                            <th width="30%">Description</th>

                        </tr>
                        <c:forEach var="currentHero" items="${heroList}">
                            <tr>
                                <td>
                                    <c:out value="${currentHero.heroName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentHero.superPower}"/>
                                </td>
                                <td>
                                    <c:out value="${currentHero.description}"/>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_USER')">
                                        <a href="displayEditHerosForm?superHeroId=${currentHero.superHeroId}">
                                            Edit
                                        </a>
                                    </sec:authorize>

                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteHero?superHeroId=${currentHero.superHeroId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> 
                <sec:authorize access="hasRole('ROLE_USER')">

                    <div class="col-md-6">
                        <h2>Add New Hero/Villain</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="createHero">
                            <div class="form-group">
                                <label for="add-name" class="col-md-4 control-label"> Name:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="heroName" placeholder="Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-power" class="col-md-4 control-label">Super Power:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="superPower" placeholder="Super Power" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-description" class="col-md-4 control-label">Description:</label>
                                <div class="col-md-8">
                                    <input type="description" class="form-control" name="description" placeholder="Description" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="submit" class="btn btn-default" value="Create Hero/Villain"/>
                                </div>
                            </div>
                        </form>

                    </div> <!-- End col div -->
                </sec:authorize>

            </div> <!-- End row div -->
            <!-- Main Page Content Stop --> 
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
