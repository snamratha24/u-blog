<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /*If user tries to click on browser back button then he/ she should not be able to access this page*/
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);
%>

<!--
	TODO: 7.16. Check if user is logged in or not. If not then redirect user to the default page i.e index.jsp.
	(Hint: You need to handle NullPointerException.)
	(Hint: Make use of the email id stored in the session object to check if user is logged in or not.)
-->
<%
    if(session.getAttribute("email")==null){
        response.sendRedirect("/");
    }
%>
<!--
	TODO: 7.17. Design the "Delete Post" page with the following properties.
	    1. Title of the page should be "Delete Post"
	    2. Provide method and action attributes of the form tag such that when user submit
	        the form, the doPost() method of PostUtilServlet get invoked.
	    3. Provide "Post Id:" label along with the text field as shown on the learn platform.
	    4. Also, for the "Post Id:" text field, provide type as number, min as 1, placeholder as
	        "Post Id" and make this field required.
	    5. Provide a "Delete" submit button.
        6. Provide a link to the "Home Page".
-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Delete Post</title>

</head>
<body>

<h3>Logged In as ${username} </h3>

<form action = "/ublog/post/util" method = "POST" >
    <table cellspacing="10">
        <tr>
            <td><label for="postid"> Post ID: </label>
            </td>
            <td>
                <input id="postid" type = "number" name = "postid" placeholder="Post ID" required min="1">
            </td>
        </tr>

        <tr>
            <td> <input type = "submit" name = "actionType" value = "Delete" />

            </td>
            <td>
                <a href="/Home.jsp">Home Page</a>
            </td>
        </tr>
        <tr>
            <td>
                <% if (request.getAttribute("errorMessage")!=null) { %>
                <%= request.getAttribute("errorMessage") %>

                <%  } %>

            </td>
            <td></td>
        </tr>

    </table>
</form>


</body>
<!--
    TODO: 7.18. If the user is logged in then display the string before @ in the user email id
    on this web page. For example, if email id is example@gmail.com, then display "Logged In as example"
    in the top left corner of the web page as shown on the learn platform.
-->

<!--
    TODO: 7.20. Check if there exists an error message in the request object. If so, then display the message
    below the Delete button.
    TODO: 7.21. Check if there exists a normal message in the request object. If so, then display the message
    below the Delete button.
-->