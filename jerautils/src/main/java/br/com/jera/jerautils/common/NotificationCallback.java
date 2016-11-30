package br.com.jera.jerautils.common;

/**
 * Created by daividsilverio on 29/11/16.
 */

public abstract class NotificationCallback implements ConfirmationCallback {

    @Override
    public void onRefuse() {
        //do nothing
    }
}
