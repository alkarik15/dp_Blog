package ru.skillbox.blog.mapper;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface Mapper <E,D> {
    E toEntity (D dto);
    D toDto (E entity);
}
