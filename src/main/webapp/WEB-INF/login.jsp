<html>
<style>
    .center {
        margin: auto;
        width: 60%;
        border: 1px solid;
        border-collapse: collapse;
        padding: 10px;
        background-color: royalblue;
        /*background:url("/khaomunkai.jpg");*/
    }
    beforeform{
        color:white;
    }

</style>
    <body><div class="center">
        <mainbody>
        <h2 style="color:white; font-size:36px;">Login</h2>
        <form action="/login" method="post">
            <beforeform>Username:</beforeform><br/>
            <input type="text" value="${username}" name="username"/>
            <br/>
            <beforeform>Password:</beforeform><br/>
            <input type="password" name="password">
            <br><br>
            <input type="submit" value="Submit">
            <input type="submit" value="Register" name = "Register">
        </form>
        <beforeform>${notification}</beforeform>
        </mainbody>
        </div>
    </body>
</html>
