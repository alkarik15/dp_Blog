package ru.skillbox.blog.dto;

import ru.skillbox.blog.dto.enums.ParametrMode;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class Param {
    private int offset;
    private int limit;
    private ParametrMode mode;

    public int getOffset() {
        return offset;
    }

    public void setOffset(final int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

    public ParametrMode getMode() {
        return mode;
    }

    public void setMode(final ParametrMode mode) {
        this.mode = mode;
    }
}
