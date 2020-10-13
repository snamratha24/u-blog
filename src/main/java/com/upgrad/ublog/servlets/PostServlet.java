package com.upgrad.ublog.servlets;

import com.upgrad.ublog.dto.PostDTO;
import com.upgrad.ublog.dto.UserDTO;
import com.upgrad.ublog.services.ServiceFactory;
import com.upgrad.ublog.utils.DateTimeFormatter;
import com.upgrad.ublog.utils.LogWriter;
import oracle.jdbc.proxy.annotation.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("email") == null) {

            response.sendRedirect("/");

        } else {

            response.setContentType("text/html");
            RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
            rd.include(request,response);
            Integer postId = 1;
            String userEmail  = session.getAttribute("email").toString();
            String title = request.getParameter("title");
            String tag = request.getParameter("tag");
            String description = request.getParameter("description");
            String btn = request.getParameter("btn");
            LocalDateTime timestampu = LocalDateTime.now();
            //SAVE TO DATABASE
            LogWriter logWriter = new LogWriter();
            ServiceFactory serviceFactory = new ServiceFactory();
            PostDTO post =new PostDTO();
            post.setEmailId(userEmail);
            post.setTitle(title);
            post.setTag(tag);
            post.setDescription(description);
            post.setPostId(postId);
            post.setTimestamp(LocalDateTime.now());
            String postLog = "<" + DateTimeFormatter.format(timestampu)
            + "><\t>New post with title [ <" + title + "> ] created by [ <" + userEmail +"> ]";
            String currentUsersHomeDir = System.getProperty("user.dir");
            System.out.println(currentUsersHomeDir);
            LogWriter.writeLog(postLog, currentUsersHomeDir);
            try {
                serviceFactory.createPostService().save(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println(serviceFactory.createPostService().save(post));
            } catch (Exception e) {
                e.printStackTrace();
            }

            session.setAttribute("post",post);
            response.sendRedirect("View.jsp");
            System.out.println("New Post Created " + post);



        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
