<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/location/displayLocationsPage">Location</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/displayOrganizationsPage">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sighting/displaySightingPage">Sightings</a></li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation" ><a href="${pageContext.request.contextPath}/displayUserList">User Admin</a></li>                        
                    </sec:authorize>
                </ul>    
            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>

            <h1>Users</h1>
            <a href="displayUserForm">Add a User</a><br/>
            <hr/>
            <table id="SightingTable" class="table table-hover">
                <tr>
                    <th width="30%">User Name</th>                                         
                    <th width="30%">Is user enabled?</th>                         
                </tr>
                <c:forEach var="currentUser" items="${usersList}">
                    <tr>
                        <td>
                            <c:out value="${currentUser.username}"/>
                        </td> 
                        <td>
                            <c:out value="${currentUser.isEnabled}"/>
                        </td>
                        <td>
                            <a href="changeStatus?userId=${currentUser.userId}">Change User Status</a><br/><br/>
                        </td>
                        <td>
                            <a href="displayEditUserForm?userId=${currentUser.userId}">Edit</a><br/><br/>
                        </td>
                        <td>
                            <a href="deleteUser?userId=${currentUser.userId}">Delete</a><br/><br/>
                        </td>

                    </tr>
                </c:forEach>
            </table> 

        </div>
        <!-- Placed at the end of the document so the pages load faster -->       
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

