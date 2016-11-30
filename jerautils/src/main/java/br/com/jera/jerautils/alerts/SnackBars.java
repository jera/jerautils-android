package br.com.jera.jerautils.alerts;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import br.com.jera.jerautils.common.ConfirmationCallback;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class SnackBars {

    @Nullable
    protected static Snackbar show(View view, final AlertConfiguration alertConfiguration, final ConfirmationCallback confirmationCallback) {
        if (configurationIsValid(alertConfiguration)) {
            showSnackBar(view, alertConfiguration, confirmationCallback);
        } else {
            throw new RuntimeException("Invalid Configuration for SnackBars! What are you trying to DO!?");
        }
        return null;
    }

    private static Snackbar showSnackBar(View view, AlertConfiguration alertConfiguration, final ConfirmationCallback confirmationCallback) {
        Snackbar snackBar = Snackbar.make(view, alertConfiguration.message, durationForSnackBar(alertConfiguration.duration));
        if (!TextUtils.isEmpty(alertConfiguration.actionText)) {
            snackBar.setAction(alertConfiguration.actionText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (confirmationCallback != null) {
                        confirmationCallback.onConfirm();
                    }
                }
            });
        }
        snackBar.show();
        return snackBar;
    }

    private static boolean configurationIsValid(AlertConfiguration configuration) {
        return configuration != null &&
                !TextUtils.isEmpty(configuration.message) &&
                hasValidDuration(configuration.duration);
    }

    private static boolean hasValidDuration(@AlertConfiguration.Duration int duration) {
        return AlertConfiguration.LENGTH_INDEFINITE <= duration && duration <= Snackbar.LENGTH_LONG;
    }

    private static int durationForSnackBar(@AlertConfiguration.Duration int duration) {
        switch (duration) {
            case AlertConfiguration.LENGTH_INDEFINITE:
                return Snackbar.LENGTH_INDEFINITE;
            case AlertConfiguration.LENGTH_SHORT:
                return Snackbar.LENGTH_SHORT;
            case AlertConfiguration.LENGTH_LONG:
                return Snackbar.LENGTH_LONG;
        }
        return Snackbar.LENGTH_SHORT;
    }
}
