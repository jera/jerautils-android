package br.com.jera.jerautils.alerts;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import br.com.jera.jerautils.common.ConfirmationCallback;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class Alerts {

    @Nullable
    public static Dialog showDialog(Context context, final AlertConfiguration configuration, final ConfirmationCallback callback) {
        return Dialogs.show(context, configuration, callback);
    }

    @Nullable
    public static Snackbar showSnackBar(View view, final AlertConfiguration alertConfiguration, final ConfirmationCallback confirmationCallback) {
        return SnackBars.show(view, alertConfiguration, confirmationCallback);
    }

    public static void toast(Context context, String text) {
        Toasts.showToast(context, text);
    }

    public static void toast(Context context, final AlertConfiguration configuration) {
        Toasts.showToast(context, configuration);
    }
}