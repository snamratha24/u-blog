package com.upgrad.ublog.servlets;

import com.upgrad.ublog.dto.PostDTO;
import com.upgrad.ublog.dto.UserDTO;
import com.upgrad.ublog.exceptions.EmailNotValidException;
import com.upgrad.ublog.exceptions.PostNotFoundException;
import com.upgrad.ublog.services.ServiceFactory;
import com.upgrad.ublog.utils.EmailValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "PostUtilServlet")
public class PostUtilServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String action = request.getParameter("actionType");


            //CHECK IF USER IS SIGNED IN OR NOT
            if (session.getAttribute("email") == null) {

                response.sendRedirect("/");
            } else {
                //CHECK WHAT ACTIONTYPE
                if (action.equals("Search")) {
                    PrintWriter Writer = response.getWriter();
                    String userEmail = request.getParameter("searchEmail");

                try {
                    EmailValidator.isValidEmail(userEmail);
                    ServiceFactory serviceFactory = new ServiceFactory();

                    List<PostDTO> searchPosts = serviceFactory.createPostService().getPostsByEmail(userEmail);
//                    System.out.println("LIST GOTT : : : : " + searchPosts);
                    if(searchPosts.isEmpty()) {

                        throw new PostNotFoundException("Sorry no posts exists for this email id");

                    } else {

                        request.setAttribute("PostFound", true);
                        request.setAttribute("searchPostResults", searchPosts);
                        request.getRequestDispatcher("/ublog/Search.jsp").forward(request,response);
                    }



                } catch (EmailNotValidException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", e.getMessage());
                    System.out.println(e.getMessage());
                    RequestDispatcher rd = request.getRequestDispatcher("../Search.jsp");
                    rd.include(request, response);

                } catch( PostNotFoundException post) {
                    request.setAttribute("isError",true);
                    request.setAttribute("errorMessage",post.getMessage());
                    request.getRequestDispatcher("/ublog/Search.jsp").forward(request,response);
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", e.getMessage());
                    System.out.println(e.getMessage());
                    RequestDispatcher rd = request.getRequestDispatcher("../Search.jsp");
                    rd.include(request, response);

                }

                    //IF SIGNED IN USER THEN SEARCH FOR POST


            }

                if (action.equals("Delete")) {

                    //dDELETING POST
                    String todeletid = request.getParameter("postid");
                    int deletid = Integer.parseInt(todeletid);
                    String emailId = String.valueOf(session.getAttribute("email"));
                    ServiceFactory serviceFactory = new ServiceFactory();
                    try {
                        boolean searchPosts = serviceFactory.createPostService().deletePost(deletid, emailId);
                        System.out.println("SEAERCHPOST" + searchPosts);
                        if (searchPosts){
                            request.setAttribute("errorMessage", "Post deleted successfully!");
                            RequestDispatcher rd = request.getRequestDispatcher("../Delete.jsp");
                            rd.include(request, response);
                        }

                        if (!searchPosts) {
                            request.setAttribute("errorMessage", "You are not authorised to delete this post");
                            RequestDispatcher rd = request.getRequestDispatcher("../Delete.jsp");
                            rd.include(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("errorMessage", e.getMessage());
                        System.out.println(e.getMessage());
                        RequestDispatcher rd = request.getRequestDispatcher("../Delete.jsp");
                        rd.include(request, response);

                    }

                }
                if (action.equals("Filter")) {
                    String tagtoDelete = request.getParameter("tag");
                    System.out.println(tagtoDelete);
                    ServiceFactory serviceFactory = new ServiceFactory();
                    try {
                        List<PostDTO> searchPostsByTag = serviceFactory.createPostService().getPostsByTag(tagtoDelete);
                        if(searchPostsByTag.isEmpty()) {

                            throw new PostNotFoundException("Sorry no posts exists for this Tag");

                        } else {

                            request.setAttribute("PostFound", true);
                        System.out.println("TAGGED POST FOUNDEEEY");
                        System.out.println(searchPostsByTag);
                            request.setAttribute("searchPostResults", searchPostsByTag);
                            request.getRequestDispatcher("/ublog/Filter.jsp").forward(request,response);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();


                }

        }


    }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
