<%@ page import="io.muic.ooc.webapp.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="io.muic.ooc.webapp.database.MySQL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script>
    function popup(){
        return confirm("Are you sure you want to delete this user?");
    }
</script>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        td {
            text-align: center;
        }
    </style>
</head>
<body>
<br>
<h2>Welcome, ${cur_user}, ${cur_userid}</h2>
email : ${cur_useremail}<br>
<br><br>
<table style="width:100%">
    <tr>
        <th>User id</th>
        <th>Username</th>
        <th>Hashedpassword</th>
        <th>Email Address</th>
        <th>Edit</th>
        <th>Remove</th>
    </tr>
    <%
        List<User> userlist = MySQL.getAllUsers();
        for(User user : userlist){
    %>
    <tr>
        <td><%=user.getId()%></td>
        <td><%=user.getUser_name()%></td>
        <td><%=user.getHashedPassword()%></td>
        <td><%=user.getEmail()%></td>
        <td><form action = "/userlist" method = "post"><input type="submit" value = "Edit"><input type="hidden" value = "<%=user.getId()%>" name = "Edit"></form></td>
        <td><form action = "/userlist" method = "post" onsubmit="return popup()"><input type="submit" value = "Remove"><input type="hidden" value = "<%=user.getId()%>" name="Remove"></form></td>
    </tr>
    <%
        }
    %>
</table>
<br><br>
<form action="/userlist" method="post">
    <input type="submit" value="add user" name = "AddUser"><input type="submit" value="logout" name = "Logout">
</form>
<p>${notification}</p>
</body>
</html>