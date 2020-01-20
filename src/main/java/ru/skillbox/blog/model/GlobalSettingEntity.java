package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
@Table(name = "global_settings")
public class GlobalSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    public GlobalSettingEntity() {
    }

    public GlobalSettingEntity(final String code, final String name, final String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public GlobalSettingEntity(final String code, final String value) {
        this.code = code;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
