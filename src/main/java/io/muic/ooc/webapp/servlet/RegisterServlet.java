package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.database.MySQL;
import io.muic.ooc.webapp.helpers.EmailValidator;
import io.muic.ooc.webapp.router.Routable;
import io.muic.ooc.webapp.service.SecurityService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping(){
        return "/register";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
        rd.include(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if(!StringUtils.isBlank(username) && !StringUtils.isBlank(password) && !StringUtils.isBlank(email) && StringUtils.isAlphanumeric(username) && StringUtils.isAlphanumeric(password) && EmailValidator.validate(email)){
            boolean created = MySQL.createNewUser(username,password,email);
            if(created){
//                MySQL.createNewUser(username,password,email);
                //implement in some carry over to login page?
                String success = "User creation successful.";
                request.setAttribute("notification",success);
                request.setAttribute("username",username);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request,response);
            }else{
                String error = "";
                if(MySQL.checkUserAlreadyExist(username)){
                    error = "This username is already in use.";
                } else if(MySQL.checkEmailAlreadyExist(email)) {
                    error = "This email address is already in use.";
                }
                request.setAttribute("notification",error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(request,response);
            }
        }else{
            String error = "Please fill in the form.";
            request.setAttribute("notification",error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(request,response);
        }
    }
}
