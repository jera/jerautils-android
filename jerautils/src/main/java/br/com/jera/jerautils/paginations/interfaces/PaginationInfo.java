package br.com.jera.jerautils.paginations.interfaces;

/**
 * Created by daividsilverio on 30/11/16.
 */
public interface PaginationInfo {
    int UNKNOWN = -1;

    int getCurrentPage();

    int getNextPage();

    int getTotalItens();

    int getTotalPages();
}
