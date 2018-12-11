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
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/location/displayLocationsPage">Location</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/organization/displayOrganizationsPage">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sighting/displaySightingPage">Sightings</a></li>
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
                <div class="col-md-6">
                    <h2>Organizations</h2>
                    <table id="OrganizationTable" class="table table-hover">
                        <tr>
                            <th width="30%">Name</th>
                            <th width="30%">Description</th>
                            <th width="30%">Address</th>
                            <th width="10%">Phone</th>

                        </tr>

                        <c:forEach var="currentOrganization" items="${organizationList}">
                            <tr>
                                <td>
                                    <c:out value="${currentOrganization.organizationName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentOrganization.description}"/>
                                </td>
                                <td>
                                    <c:out value="${currentOrganization.address}"/>
                                </td>
                                <td>
                                    <c:out value="${currentOrganization.phone}"/>
                                </td>

                                <td>
                                    <a href="displayOrganizationMembers?organizationId=${currentOrganization.organizationId}">
                                        Members
                                    </a>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="displayEditOrganizationsForm?organizationId=${currentOrganization.organizationId}">
                                            Edit
                                        </a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteOrganization?organizationId=${currentOrganization.organizationId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> 



                <div class="col-md-3">            
                    <div class="row">
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="addMember">
                            <div class="form-group">
                                <label for="add-hero" class="col-md-4 control-label">Hero/Villain:</label>
                                <select name="superHeroId">
                                    <c:forEach var="currentHero" items="${herosList}">
                                        <option value="${currentHero.superHeroId}"/>
                                        <c:out value="${currentHero.heroName}" />                                
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="add-organization" class="col-md-4 control-label">Organization:</label>
                                <select name="organizationId">
                                    <c:forEach var="currentOrganization" items="${organizationList}">
                                        <option value="${currentOrganization.organizationId}"/>
                                        <c:out value="${currentOrganization.organizationName}" />                                
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="submit" class="btn btn-default" value="Add Member"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>



                <div class="col-md-3">
                    <div class="row">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <h2>Add New Organization</h2>
                            <form class="form-horizontal" 
                                  role="form" method="POST" 
                                  action="createOrganization">
                                <div class="form-group">
                                    <label for="add-organization-name" class="col-md-4 control-label"> Organization name:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="organizationName" placeholder="Name" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add-description" class="col-md-4 control-label">Description:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="description" placeholder="Description" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add-address" class="col-md-4 control-label">Address:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="address" placeholder="Address" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add-phone" class="col-md-4 control-label">Phone:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="phone" placeholder="Phone" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <input type="submit" class="btn btn-default" value="Create Organization"/>
                                    </div>
                                </div>
                            </form>
                        </sec:authorize>
                    </div> <!-- End col div -->
                </div>
            </div> <!-- End row div -->
            <!-- Main Page Content Stop --> 
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
