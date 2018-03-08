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
<h2>Edit User</h2>
<form action="/edituser" method="post">
    <input type="hidden" value=${editing_id} name="editing_id">
    Editing User Id : ${editing_id}<br>
    Username:<br/>
    <input type="text" value="${editing_username}" name="username"/>
    <br/>
    Password:<br/>
    <input type="text" name="password">
    <br/>
    Email Address:<br/>
    <input type="text" value="${editing_email}" name="email">
    <br/>
    <br><br>
    <input type="submit" value="Submit">
</form>
<p>${notification}</p>
</body>
</html>