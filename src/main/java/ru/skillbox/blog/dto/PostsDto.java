package ru.skillbox.blog.dto;

import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class PostsDto {
    private Integer count;

    private List<PostDto> posts;


    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(final List<PostDto> posts) {
        this.posts = posts;
    }

    public PostsDto(final int count, final List<PostDto> posts) {
        this.count = count;
        this.posts = posts;
    }


}
