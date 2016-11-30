package br.com.jera.jerautils.alerts;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.annotation.RestrictTo.Scope.GROUP_ID;

/**
 * Created by daividsilverio on 29/11/16.
 */

public class AlertConfiguration {

    public String title;
    public String message;
    public String actionText;
    public String cancelText;

    @Snackbar.Duration
    public int duration = LENGTH_SHORT;

    private AlertConfiguration() {
    }

    public static class Builder {

        private AlertConfiguration configuration;

        public Builder() {
            configuration = new AlertConfiguration();
        }

        public AlertConfiguration build() {
            return configuration;
        }

        public Builder withTitle(String title) {
            configuration.title = title;
            return this;
        }

        public Builder withMessage(String message) {
            configuration.message = message;
            return this;
        }

        public Builder withDuration(@Duration int duration) {
            configuration.duration = duration;
            return this;
        }

        public Builder withActionText(String actionText) {
            configuration.actionText = actionText;
            return this;
        }

        public Builder withCancelText(String cancelText) {
            configuration.cancelText = cancelText;
            return this;
        }

        public Builder withTitle(Context context, @StringRes int titleResId) {
            configuration.title = context.getString(titleResId);
            return this;
        }

        public Builder withMessage(Context context, @StringRes int messageResId) {
            configuration.message = context.getString(messageResId);
            return this;
        }

        public Builder withActionText(Context context, @StringRes int actionTextResId) {
            configuration.actionText = context.getString(actionTextResId);
            return this;
        }

        public Builder withCancelText(Context context, @StringRes int cancelTextResId) {
            configuration.cancelText = context.getString(cancelTextResId);
            return this;
        }
    }

    @RestrictTo(GROUP_ID)
    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;
}
