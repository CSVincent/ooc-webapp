<html>
<style>
    .center {
        margin: auto;
        width: 60%;
        border: 1px solid;
        border-collapse: collapse;
        padding: 10px;
    }
</style>
<body class="center">
<h2>Add User</h2>
<form action="/adduser" method="post">
    Username:<br/>
    <input type="text" name="username"/>
    <br/>
    Password:<br/>
    <input type="text" name="password">
    <br/>
    Email Address:<br/>
    <input type="text" name="email">
    <br/>
    <br><br>
    <input type="submit" value="Submit">
</form>
<p>${notification}</p>
</body>
</html>