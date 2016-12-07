package br.com.jera.jerautils.alerts;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class Toasts {

    protected static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("WrongConstant")
    protected static void showToast(Context context, AlertConfiguration configuration) {
        if (configurationIsValid(configuration)) {
            Toast.makeText(context, configuration.message, durationForToast(configuration.duration)).show();
        }
    }

    private static boolean configurationIsValid(AlertConfiguration configuration) {
        return configuration != null &&
                !TextUtils.isEmpty(configuration.message) &&
                hasValidDuration(configuration.duration);
    }

    private static boolean hasValidDuration(@AlertConfiguration.Duration int duration) {
        return AlertConfiguration.LENGTH_SHORT <= duration && duration <= Snackbar.LENGTH_LONG;
    }

    private static int durationForToast(@AlertConfiguration.Duration int duration) {
        switch (duration) {
            case AlertConfiguration.LENGTH_SHORT:
                return Toast.LENGTH_SHORT;
            case AlertConfiguration.LENGTH_LONG:
                return Toast.LENGTH_LONG;
        }
        return Toast.LENGTH_SHORT;
    }
}
