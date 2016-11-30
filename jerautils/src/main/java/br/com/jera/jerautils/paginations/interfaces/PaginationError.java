package br.com.jera.jerautils.paginations.interfaces;

/**
 * Created by daividsilverio on 30/11/16.
 */
public interface PaginationError {
    Exception getCause();
    String getErrorMessage();
}
