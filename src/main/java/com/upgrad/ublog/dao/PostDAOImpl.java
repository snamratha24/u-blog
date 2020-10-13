package com.upgrad.ublog.dao;

/**
 * TODO: 6.19. Implement the PostsDAO interface and implement this class using the Singleton pattern.
 *  (Hint: Should have a private no-arg Constructor, a private static instance attribute of type
 *  PostDAOImpl and a public static getInstance() method which returns the instance attribute.)
 *   Note: getPostDAO() method of the DAOFactory should return the PostDAOImpl object using
 *   getInstance() method of the PostDAOImpl class
 * TODO: 6.20. Define the following methods and return null for each of them. You will provide a
 *  proper implementation for each of these methods, later in this project.
 *  a. findByEmail()
 *  b. findByTag()
 *  c. findAllTags()
 *  d. findById()
 *  e. deleteById() (return false for this method for now)
 * TODO: 6.21. create() method should take post details as input and insert these details into
 *  the UBLOG_POSTS table. Return the same PostDTO object which was passed as an input argument.
 *  (Hint: You should get the connection using the DatabaseConnection class)
 */

/**
 * TODO: 7.1. Implement findByEmail() method which takes email id as an input parameter and
 *  returns all the posts corresponding to the email id from the UBLOG_POSTS table defined
 *  in the database.
 */

/**
 * TODO: 7.13. Implement the deleteById() method which takes post id as an input argument and delete
 *  the corresponding post from the database. If the post was deleted successfully, then return true,
 *  otherwise, return false.
 * TODO: 7.14. Implement the findById() method which takes post id as an input argument and return the
 *  post details from the database. If no post exists for the given id, then return an PostDTO object
 *  without setting any of its attributes.
 */

import com.sun.mail.imap.Rights;
import com.upgrad.ublog.db.DatabaseConnection;
import com.upgrad.ublog.dto.PostDTO;
import com.upgrad.ublog.dto.UserDTO;
import com.upgrad.ublog.services.UserServiceImpl;
import com.upgrad.ublog.utils.DateTimeFormatter;
import oracle.jdbc.proxy.annotation.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: 7.22. Implement findAllTags() method which returns a list of all unique tags in the UBLOG_POSTS
 *  table.
 * TODO: 7.23. Implement findByTag() method which takes "tag" as an input argument and returns all the
 *  posts corresponding to the input "tag" from the UBLOG_POSTS table defined in the database.
 */

public class PostDAOImpl implements PostDAO {

    PostDAOImpl() {
    }

    private static PostDAOImpl instance;

    public static PostDAOImpl getInstance() {

        if (instance == null) {
            instance = new PostDAOImpl();
        }
        return instance;
    }


    @Override
    public PostDTO create(PostDTO postDTO) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        int postId = postDTO.getPostId();
        String emailId = postDTO.getEmailId();
        String title = postDTO.getTitle();
        String tag = postDTO.getTag();
        String description = postDTO.getDescription();
        LocalDateTime timestamp = postDTO.getTimestamp();
        String query = "INSERT INTO ublog_posts(id, email_id, title, description, tag, TIMESTAMP )" + "VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,postId);
        statement.setString(2,emailId);
        statement.setString(3,title);
        statement.setString(5,tag);
        statement.setString(4,description);
        statement.setTimestamp(6, Timestamp.valueOf(timestamp));
        statement.execute();
        return postDTO;

    }

    @Override
    public List<PostDTO> findByEmail(String emailId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        List<PostDTO> postDTOS = new ArrayList<PostDTO>();
        try {
            String selectQuery = "select id, email_id, title, description, tag, to_date('2014-07-11 12:00:00', 'YYYY-MM-DD HH24:MI:SS') as mydate from ublog_posts WHERE EMAIL_ID ='" + emailId + "'" ;
//        System.out.println(selectQuery);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            int i = 0;

            while (resultSet.next()) {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setPostId(resultSet.getInt("id"));
                    postDTO.setEmailId(resultSet.getString("email_id"));
                    postDTO.setTitle(resultSet.getString("title"));
                    postDTO.setDescription(resultSet.getString("description"));
                    postDTO.setTag(resultSet.getString("tag"));
//            System.out.println("GOING TO FIND TIMESTAMP");
                    postDTO.setTimestamp(resultSet.getTimestamp("mydate").toLocalDateTime());
                    postDTOS.add(i, postDTO);
                    i++;
//                postDTOS.addAll(Collections.singleton(postDTO));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            return postDTOS;
        }


//        System.out.println("PRINTING THIS METHOD");
//        System.out.println(postDTOS);
//        return postDTOS;

    }

    @Override
    public List<PostDTO> findByTag(String tag) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        List<PostDTO> postDTOS = new ArrayList<PostDTO>();
        try {
            String selectQuery = "select id, email_id, title, description, tag, to_date('2014-07-11 12:00:00', 'YYYY-MM-DD HH24:MI:SS') as mydate from ublog_posts WHERE tag ='" + tag + "'" ;
//        System.out.println(selectQuery);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            int i = 0;

            while (resultSet.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostId(resultSet.getInt("id"));
                postDTO.setEmailId(resultSet.getString("email_id"));
                postDTO.setTitle(resultSet.getString("title"));
                postDTO.setDescription(resultSet.getString("description"));
                postDTO.setTag(resultSet.getString("tag"));
//            System.out.println("GOING TO FIND TIMESTAMP");
                postDTO.setTimestamp(resultSet.getTimestamp("mydate").toLocalDateTime());
                postDTOS.add(i, postDTO);
                i++;
//                postDTOS.addAll(Collections.singleton(postDTO));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            return postDTOS;
        }

    }

    @Override
    public PostDTO findById(int id) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PostDTO postDTO = new PostDTO();
        try {
            String selectQuery = "select id, email_id, title, description, tag, to_date('2014-07-11 12:00:00', 'YYYY-MM-DD HH24:MI:SS') as mydate from ublog_posts WHERE ID ='" + id + "'" ;
        System.out.println(selectQuery);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                postDTO.setPostId(resultSet.getInt("id"));
                postDTO.setEmailId(resultSet.getString("email_id"));
                postDTO.setTitle(resultSet.getString("title"));
                postDTO.setDescription(resultSet.getString("description"));
                postDTO.setTag(resultSet.getString("tag"));
                postDTO.setTimestamp(resultSet.getTimestamp("mydate").toLocalDateTime());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return postDTO;

    }

    @Override
    public List<String> findAllTags() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        List<String> allTags = new ArrayList<String>();
        try {
            String selectQuery = "select tag from ublog_posts GROUP BY tag ORDER BY tag ASC " ;
//        System.out.println(selectQuery);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            int i = 0;

            while (resultSet.next()) {
                allTags.add(i, resultSet.getString("tag"));
                i++;
//                postDTOS.addAll(Collections.singleton(postDTO));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            System.out.println("FOUND ALL TAGS" + allTags);
            return allTags;
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {

        //GET CoNNECTON
        Connection connection = DatabaseConnection.getConnection();
        int postId = id;
        try {
            String deleteQuery = "DELETE FROM ublog_posts WHERE ID ='" + postId + "'" ;
        System.out.println(deleteQuery);
            Statement statement = connection.createStatement();
            int count = statement.executeUpdate(deleteQuery);
            System.out.println(count);

            if (count == 1) {
                return true;
            } else {
                return false;
            }

             } catch (SQLException ex) {

                 throw new SQLException(ex);
            }

    }


}
