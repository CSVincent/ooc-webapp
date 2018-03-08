package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.database.MySQL;
import io.muic.ooc.webapp.entities.User;
import io.muic.ooc.webapp.router.Routable;
import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping(){
        return "/userlist";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if(authorized){
            String curUsername = (String) request.getSession().getAttribute("username");
            User curUser = UserService.getUser(curUsername);
            request.getSession().setAttribute("cur_user",curUser.getUser_name());
            request.getSession().setAttribute("cur_userid",curUser.getId());
            request.getSession().setAttribute("cur_useremail",curUser.getEmail());
            request.setAttribute("cur_user",request.getSession().getAttribute("username"));
            request.setAttribute("cur_userid",request.getSession().getAttribute("cur_userid"));
            request.setAttribute("cur_useremail",request.getSession().getAttribute("cur_useremail"));
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
            rd.include(request, response);
        }else{
            response.sendRedirect("/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        boolean authorized = securityService.isAuthorized(request);
        if(authorized){
            if(request.getParameter("Logout")!=null){
                securityService.logout(request);
                response.sendRedirect("/login");
            }
            if(request.getParameter("Remove")!=null){
                String removingId = request.getParameter("Remove");
                User requsetingUser = UserService.getUser((String)request.getSession().getAttribute("username"));
                User removingUser = UserService.getUserById(removingId);
                if(!((requsetingUser.getId()) == (removingUser.getId()))){
                    boolean removed = MySQL.removeUserbyUsername(removingUser.getUser_name());
                    if(removed){
                        String success = "User removed";
                        request.setAttribute("notification",success);
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
                        rd.include(request,response);
                    }else{
                        String error = "Failure in removing the user.";
                        request.setAttribute("notification",error);
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
                        rd.include(request,response);
                    }
                }else{
                    String error = "Cannot remove self from the userlist.";
                    request.setAttribute("notification",error);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
                    rd.include(request,response);
                }
            }
            if(request.getParameter("AddUser")!=null){
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/adduser.jsp");
                rd.include(request,response);
            }
            if(request.getParameter("Edit")!=null){
                String editingId = request.getParameter("Edit");
                User editingUser = UserService.getUserById(editingId);
                request.setAttribute("editing_username",editingUser.getUser_name());
                request.setAttribute("editing_email",editingUser.getEmail());
                request.setAttribute("editing_id",editingId);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                rd.include(request,response);

            }
        }else {
            String error = "user authorization error";
            request.setAttribute("notification",error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request,response);
        }
    }
}
