package br.com.jera.jerautils.paginations.interfaces;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by daividsilverio on 30/11/16.
 */

public interface DataSourceCallback<T> {
    void onSuccess(List<T> items, @Nullable PaginationInfo paginationInfo);
    void onFailure(PaginationError paginationError, @Nullable PaginationInfo paginationInfo);
}
