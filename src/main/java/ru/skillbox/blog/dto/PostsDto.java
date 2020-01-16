package ru.skillbox.blog.dto;

import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class PostsDto {
    private int cout;
    private List<PostDto> posts;

    public int getCout() {
        return cout;
    }

    public void setCout(final int cout) {
        this.cout = cout;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(final List<PostDto> posts) {
        this.posts = posts;
    }

    public PostsDto(final int cout, final List<PostDto> posts) {
        this.cout = cout;
        this.posts = posts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PostsDto{");
        sb.append("cout=").append(cout);
        sb.append(", posts=").append(posts);
        sb.append('}');
        return sb.toString();
    }
}
