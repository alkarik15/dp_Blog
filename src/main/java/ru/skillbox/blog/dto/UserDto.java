package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class UserDto {
    private int id;

    private String name;

    private String photo;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }
}
