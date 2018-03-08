<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <body>
    <h2>Welcome, ${username}</h2>
    <form action="/index.jsp" method="post">
        <input type="submit" value="logout" name = "Logout"> <input type="submit" value="user" name = "User">
    </form>
    <p>${notification}</p>
    </body>
</html>
