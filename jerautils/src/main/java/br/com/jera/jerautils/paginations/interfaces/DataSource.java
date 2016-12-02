package br.com.jera.jerautils.paginations.interfaces;

/**
 * Created by daividsilverio on 30/11/16.
 */

public interface DataSource<T> {
    void fetchData(int page, Integer pageSize, DataSourceCallback<T> callback);
}
