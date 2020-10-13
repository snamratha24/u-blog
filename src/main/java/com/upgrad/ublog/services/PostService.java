package com.upgrad.ublog.services;

import com.upgrad.ublog.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO save(PostDTO postDTO) throws Exception;
    List<PostDTO> getPostsByEmail(String emailId) throws Exception;
    List<PostDTO> getPostsByTag(String tag) throws Exception;
    List<String> getAllTags() throws Exception;
    boolean deletePost(int id, String emailId) throws Exception;
}
