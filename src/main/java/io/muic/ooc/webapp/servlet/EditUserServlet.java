package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.database.MySQL;
import io.muic.ooc.webapp.entities.User;
import io.muic.ooc.webapp.helpers.EmailValidator;
import io.muic.ooc.webapp.router.Routable;
import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping(){
        return "/edituser";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        boolean isAuthorized = securityService.isAuthorized(request);
        if(isAuthorized){
            if(request.getAttribute("editing_id")!=null){
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                rd.include(request,response);
            }else{
                String error = "No editing id";
                request.setAttribute("notification",error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
                rd.include(request,response);
            }
        }else{
            response.sendRedirect("/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        boolean authorized = securityService.isAuthorized(request);
        if(authorized){
            String editing_id = request.getParameter("editing_id");
            System.out.println("editing_id = " + editing_id);
            String newUsername = request.getParameter("username");
            String newPassword = request.getParameter("password");
            String newEmail = request.getParameter("email");
            User beforeEdit = UserService.getUserById(editing_id);
            if(!StringUtils.isBlank(newUsername) && !StringUtils.isBlank(newPassword) && !StringUtils.isBlank(newEmail) && StringUtils.isAlphanumeric(newUsername) && StringUtils.isAlphanumeric(newPassword) && EmailValidator.validate(newEmail)){
                boolean modified = MySQL.modifyUserById(editing_id,newUsername,newPassword,newEmail);
                if(modified){
                    if(request.getSession().getAttribute("username").equals(beforeEdit.getUser_name())){
                        request.getSession().setAttribute("username",newUsername);
                    }
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
                    rd.include(request,response);
                }else{
                    String error = "Failure updating the user (Username already existed and/or email is already in use)";
                    request.setAttribute("notification",error);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
                    rd.include(request,response);
                }
            }else{
                String error = "Please fill in the form.";
                request.setAttribute("notification",error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                rd.include(request,response);
            }
        }else{
            String error = "user authorization error";
            request.setAttribute("notification",error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request,response);
        }
    }
}
