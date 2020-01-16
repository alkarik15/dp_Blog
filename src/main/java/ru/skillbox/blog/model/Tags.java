package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
//    private Set<Posts> posts =new HashSet<>();

    public Tags() {
    }

    public Tags(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
