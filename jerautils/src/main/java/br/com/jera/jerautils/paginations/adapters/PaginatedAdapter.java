package br.com.jera.jerautils.paginations.adapters;

import java.util.List;

/**
 * Created by daividsilverio on 01/12/16.
 */
public interface PaginatedAdapter<T> {
    void addItems(List<T> items);
}
