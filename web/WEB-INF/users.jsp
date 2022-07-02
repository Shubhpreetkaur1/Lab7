
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>users</title>
         <link type="text/css" rel="stylesheet" href="style.css">
    </head>
    <body>
        <div class="add">
            <form method="post" action="user">
                <h2>
                Add Users
                </h2>
                <input type="email" name="a_email" value="" placeholder="Email">
                <br>
                <input type="text" name="a_fname" value="" placeholder="First Name">
                <br>
                <input type="text" name="a_lname" value="" placeholder="Last Name">
                <br>
                <input type="password" name="a_password" value="" placeholder="Password">
                <br>
                <select name="role">
                    <c:forEach items="${roles}" var="role">
                        <option>
                            ${role.role}
                        </option>
                    </c:forEach>
                </select>
                <br>
                <input type="submit" value="Save">
                <input type="hidden" name="action" value="add">
            </form>
        </div>
        
        <div class="manage">
            <h2>Manage users</h2>
            <table>
                <tr>
                    <th>
                        Email
                    </th>
                    <th>
                        First Name
                    </th>
                    <th>
                        Last Name
                    </th>
                    <th>
                        Role
                    </th>
                    <th>
                        Edit
                    </th>
                    <th>
                        Delete
                    </th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>
                            ${user.email}
                        </td>
                        <td>
                            ${user.first_name}
                        </td>
                        <td>
                            ${user.last_name}
                        </td>
                        <td>
                            ${user.role}
                        </td>
                        <td>
                            <a href="user?action=edit&amp;email=${user.email}">#</a>
                        </td>
                        <td>
                            <a href="user?action=delete&amp;email=${user.email}">*</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
                        
        <div class="edit">
            <h2>Edit users</h2>
            <form method="post" action="user">
                <input type="email" name="e_email" value="" placeholder="Email">
                <br>
                <input type="text" name="e_fname" value="" placeholder="First Name">
                <br>
                <input type="text" name="e_lname" value="" placeholder="Last Name">
                <br>
                <select class="select" name="e_role">
                    <option value="">Select</option>
                    <c:forEach items="${roles}" var="role">
                        <c:choose>
                            <c:when test="${role.role==e_role}">
                                <option selected>${role.role}</option>
                            </c:when>
                            <c:otherwise>
                                <option>${role.role}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <br>
                <input type="submit" name="update" value="Save">
                <input type="hidden" name="action" value="update">
                <br>
                <input type="submit" name="edit" value="cancel">
                
            </form>
        </div>               
    </body>                   
</html>
