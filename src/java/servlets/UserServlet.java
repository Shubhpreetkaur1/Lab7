
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;


public class UserServlet extends HttpServlet {
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       UserService userservice = new UserService();
       RoleService roleservice = new RoleService();
       String action = request.getParameter("action");
       try{
           if(action != null){
               if(action.equals("edit")){
                   String email= request.getParameter("email");
                   request.setAttribute("e_email", email);
               }
               if(action.equals("delete")){
                  //userservice.delete(email);
                 // request.setAttribute("message", );
               }
           }}catch(Exception ex){
             Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);  
           }
       try{
             List<User> users = userservice.getAll();
            request.setAttribute("users", users);
            
            List<Role> roles = roleservice.getAll();
            request.setAttribute("roles", roles);
            
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
       return;
       }
       catch(Exception ex){
           Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex); 
       }
        
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      /** HttpSession session= request.getSession();
       UserService userservice = new UserService();
       RoleService roleservice = new RoleService();
       String action = request.getParameter("action");
       String email;
       String fname;
       String lname;
       String password;
       int role_id;
       String role;
       
       try{
           switch(action){
               case "add":
                   email= request.getParameter("a_email");
                   fname= request.getParameter("a_fname");
                   lname= request.getParameter("a_lname");
                   password = request.getParameter("a_password");
                   role_id= Integer.parseInt(request.getParameter("role"));
                   switch(role_id){
                       case 1:
                           role = "system admin";
                           break;
                       case 2:
                           role ="regular user";
                           break;
                       case 3:
                           role="company admin";
                           break;
                   }
              
               case "edit":
           }
             List<User> users = userservice.getAll();
            request.setAttribute("users", users);
            
            List<Role> roles = roleservice.getAll();
            request.setAttribute("roles", roles);
       }
       catch(Exception ex){
           Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("action", null);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
       }*/
    
    
    
    
    
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        
        if(request.getParameter("action").equals("add")){
            
            String email = request.getParameter("a_email").trim();
            String fName = request.getParameter("a_fname");
            String lName = request.getParameter("a_lname");
            String password = request.getParameter("a_password");
            String role = request.getParameter("role");
           
            
            // check if any of the fields is null
            if(email == null || email.equals("") || fName == null || fName.equals("") || lName == null || lName.equals("") || password == null || password.equals("") || role == null || role.equals("") ){
                request.setAttribute("aemail", email);
                request.setAttribute("afname", fName);
                request.setAttribute("alname", lName);
                request.setAttribute("apassword", password);
                request.setAttribute("arole", role);
                request.setAttribute("message", "inputError");
                
                try {
                    List<User> users = us.getAll();
                    request.setAttribute("users", users);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "error");
                }
                
                // Load the JSP and stop the code call
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;
            }
            
            try {
                if(us.get(email) != null){
                    request.setAttribute("message", "exists");
                  
                    try {
                        List<User> users = us.getAll();
                        request.setAttribute("users", users);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("message", "error");
                    }

                    // Load the JSP and stop the code call
                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                Role role_id = rs.get(role);
                us.insert(email,  fName, lName, password, role_id.getRole_id());
                request.setAttribute("messge", "create");
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "error");
            }
        }
        
        
        else if(request.getParameter("action").equals("update")){
            
            String fName = request.getParameter("e_fname");
            String lName = request.getParameter("e_lname");
            String role = request.getParameter("e_role");
            String password = request.getParameter("e_password");
           
           
            if(fName == null || fName.equals("") || lName == null || lName.equals("") || role == null || role.equals("") ){
                request.setAttribute("u_fname", fName);
                request.setAttribute("u_lname", lName);
                request.setAttribute("u_role", role);
                request.setAttribute("message", "inputError");
                
                try {
                List<User> users = us.getAll();
                request.setAttribute("users", users);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "error");
                }
                
                // Load the JSP and stop the code call
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;
            }
            
            try {
                Role role_id = rs.get(role);
                HttpSession edit_session = request.getSession(true);
                String email = edit_session.getAttribute("edit_user").toString();
                us.update(email, fName, lName, password, role_id.getRole_id());
                request.setAttribute("message", "update");
                edit_session.invalidate();
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "error");
            }
        }
        
        // show all the users
        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        // Load the JSP and stop the code call
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;       
    }
    }

    
   


