package br.com.jera.jerautils.alerts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.Date;

import br.com.jera.jerautils.common.ConfirmationCallback;

/**
 * Created by daividsilverio on 28/11/16.
 */

public class Dialogs {

    private static Date lastDialogOpened = null;

    @Nullable
    protected static Dialog show(Context context, final AlertConfiguration configuration, final ConfirmationCallback callback) {
        if (configurationIsValid(configuration)) {
            showDialog(context, configuration, callback);
        } else {
            throw new RuntimeException("Invalid Configuration for Dialogs! What are you trying to DO!?");
        }
        return null;
    }

    @Nullable
    private static Dialog showDialog(Context context, AlertConfiguration configuration, final ConfirmationCallback callback) {
        if (!((lastDialogOpened == null) || (new Date().getTime() - lastDialogOpened.getTime() > 300))) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(configuration.title)
                .setMessage(configuration.message)
                .setPositiveButton(configuration.actionText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callback != null) {
                            callback.onConfirm();
                        }
                    }
                });
        if (!TextUtils.isEmpty(configuration.cancelText)) {
            builder.setNegativeButton(configuration.cancelText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null) {
                        callback.onRefuse();
                    }
                }
            });
        }
        lastDialogOpened = new Date();
        return builder.show();
    }

    private static boolean configurationIsValid(AlertConfiguration configuration) {
        return configuration != null &&
                !TextUtils.isEmpty(configuration.title) &&
                !TextUtils.isEmpty(configuration.message) &&
                !TextUtils.isEmpty(configuration.actionText);
    }
}
