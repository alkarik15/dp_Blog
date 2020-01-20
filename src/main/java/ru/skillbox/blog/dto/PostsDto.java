package ru.skillbox.blog.dto;

import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class PostsDto {
    private Integer count;
    private List<PostDto> posts;

    public Integer getCout() {
        return count;
    }

    public void setCout(final Integer cout) {
        this.count = cout;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(final List<PostDto> posts) {
        this.posts = posts;
    }

    public PostsDto(final int cout, final List<PostDto> posts) {
        this.count = cout;
        this.posts = posts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PostsDto{");
        sb.append("cout=").append(count);
        sb.append(", posts=").append(posts);
        sb.append('}');
        return sb.toString();
    }
}
