package com.upgrad.ublog.servlets;

import com.upgrad.ublog.dao.UserDAO;
import com.upgrad.ublog.dao.UserDAOImpl;
import com.upgrad.ublog.db.DatabaseConnection;
import com.upgrad.ublog.dto.UserDTO;
import com.upgrad.ublog.exceptions.EmailNotValidException;
import com.upgrad.ublog.services.ServiceFactory;
import com.upgrad.ublog.services.UserService;
import com.upgrad.ublog.utils.EmailValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.jms.Connection;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * TODO: 4.5. Modify the class definition to make it a Servlet class (through inheritance) and
 *  override doPost() method from the base class.
 * TODO: 4.6. Retrieve the values of form attributes defined in the index.jsp file
 * TODO: 4.7. Check if password is empty or null. If empty or null, then redirect to
 *  the index.jsp file with an error message "Password is a required field".
 *  (Hint: Store the error message as an attribute inside the request object before redirecting
 *  to the index.jsp. This error message will be displayed in the index.jsp page when this
 *  error arises.)
 * TODO: 4.8. If Sign In button is clicked, print "User Signed In" with the user
 *  details on the console. Also, store the email id in the session object.
 * TODO: 4.9. If Sign Up button is clicked, then print "User Signed Up" with the
 *  user details on the console. Also, store the email id in the session object.
 * TODO: 4.10. Check if the user is logged in or not. If yes, then redirect them
 *  to the Home.jsp file. (Hint: Make use of the email id stored in the session object to check if
 *  the user is logged in or not. This email id should be stored in the session object when the user
 *  successfully sign in or sign up.)
 */

/**
 * TODO: 5.4. Validate the email id that is retrieved from the request object using the
 *  EmailValidator class. If the email is not valid, then redirect the user to the Sign In/
 *  Sign Up page with the error message that is stored in the EmailNotValidException. This error
 *  message should be displayed on the index.jsp page.
 *  Note: Add the return statement after you redirect to the index.jsp page, otherwise you may get error
 * TODO: 5.5. Map this Servlet to "/ublog/user" url using the @WebServlet annotation.
 * TODO: 5.6: Remove the same mapping from the Deployment Descriptor otherwise, you will get an error.
 */

/**
 * TODO: 6.16. When the user click on the Sign In button on the Sign In/ Sign Up page, handle the
 *  following scenarios. (Hint: Use ServiceFactory to get UserService. Override the init() method
 *  to instantiate the UserService attribute.)
 *  1. If the user's email is not found in the database, display "No user registered with the given email address!"
 *   message on the Sign In/ Sign Up page. (Hint: You should load this message in the request object as an attribute
 *   and redirect to the index.jsp page.)
 *  2. If the user's email is registered but the password is incorrect, display "Please enter valid credentials"
 *   message on the Sign In/ Sign Up page.
 *  3. If the user's credentials are correct, then redirect the user to the Home.jsp page.
 *   Note: In this if condition, you should set the email id in the session object.
 *
 * TODO: 6.17. When the user click on the Sign Up button on the Sign In/ Sign Up page, handle the
 *  following scenarios.
 *  1. If the user's email is already registered on the database, display
 *   "A user with this email address already exists!" message on the Sign In/ Sign Up page.
 *  2. If the user's email is unregistered, then store the user's details in the database and
 *   redirect the user to the Home.jsp page.
 *   Note: In this if condition, you should set the email id in the session object.
 *
 *  TODO 6.18: If UserService is not able to process the request and throws an exception, get the
 *   message stored in the exception object and display the same message on the index.jsp page.
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet{

//     doPost() -
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // reportType("doPost", response);
        response.setContentType("text/html");
        PrintWriter Writer = response.getWriter();

        String userEmail = request.getParameter("useremail");
        String userPasswod = request.getParameter("password");
        String btn = request.getParameter("btn");
        HttpSession session=request.getSession();

        if (userPasswod == null) {

            request.setAttribute("errorMessage", "Password is a required field");
            RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
            rd.include(request,response);
            // response.sendRedirect(request.getContextPath() + "index.jsp");

        } else if (btn.equals("Signin")) {
            try {
                EmailValidator.isValidEmail(userEmail);
//                CHECK IF USER EXISTS IN DATABASE
                ServiceFactory serviceFactory = new ServiceFactory();
                UserDTO newUser = serviceFactory.createUserService().findByEmail(userEmail);
                if (newUser.getEmailId() == null) {
                        //TOT
                    request.setAttribute("errorMessage", "No user registered with the given email address!");
                    RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
                    rd.include(request,response);
                } else {

                    if (!newUser.getPassword().equals(userPasswod)) {

                        request.setAttribute("errorMessage", "Please enter valid credentials");
                        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                        rd.include(request, response);
                    } else {

//                System.out.println();
                        session.setAttribute("email", userEmail);
                        System.out.println("User Signed In vvvvvvv" + userEmail);
                        RequestDispatcher rd = request.getRequestDispatcher("../index.jsp");
                        rd.include(request, response);
                    }
                }

            } catch(EmailNotValidException exception) {

                request.setAttribute("errorMessage", exception.getMessage());
                System.out.println(exception.getMessage());
                RequestDispatcher rd=request.getRequestDispatcher("../index.jsp");
                rd.include(request,response);
            }
            catch (Exception e) {
                System.out.println("WHAT AN ASS");
                e.printStackTrace();
                System.out.println(e);

            }
            //IF EMAIL VALIDATION IS SUCCESS

        } else if (btn.equals("Signup")){

            try {

                EmailValidator.isValidEmail(userEmail);

                ServiceFactory serviceFactory = new ServiceFactory();
                UserDTO newUser = serviceFactory.createUserService().findByEmail(userEmail);
                if(newUser.getEmailId() != null) {

                    request.setAttribute("errorMessage", "A user with this email address already exists!");
                    RequestDispatcher rd = request.getRequestDispatcher("../index.jsp");
                    rd.include(request, response);

                } else {

                    UserDTO temp = new UserDTO();
//                    temp.setUserId(1);
                    temp.setEmailId(userEmail);
                    temp.setPassword(userPasswod);

                    serviceFactory.createUserService().saveUser(temp);

                    session.setAttribute("email", userEmail);

                    System.out.println("User Signed Up " + userEmail);
                    RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");

                    rd.include(request,response);

                }


            }  catch (EmailNotValidException | SQLException exception) {
                request.setAttribute("errorMessage", exception.getMessage());
                System.out.println(exception.getMessage());
                RequestDispatcher rd=request.getRequestDispatcher("../index.jsp");
                rd.include(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        if (session.getAttribute("email") != null) {

            response.sendRedirect("/Home.jsp");

        }


}




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
