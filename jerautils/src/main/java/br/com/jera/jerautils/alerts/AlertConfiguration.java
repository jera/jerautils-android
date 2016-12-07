package br.com.jera.jerautils.alerts;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
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

    protected String title;
    protected String message;
    protected String actionText;
    protected String cancelText;

    @Snackbar.Duration
    protected int duration = LENGTH_SHORT;

    private AlertConfiguration() {
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getActionText() {
        return actionText;
    }

    @Nullable
    public String getCancelText() {
        return cancelText;
    }

    @Nullable
    public int getDuration() {
        return duration;
    }

    private AlertConfiguration(AlertConfiguration alertConfiguration) {
        this();
        this.title = alertConfiguration.title;
        this.message = alertConfiguration.message;
        this.actionText = alertConfiguration.actionText;
        this.cancelText = alertConfiguration.cancelText;
        this.duration = alertConfiguration.duration;
    }

    public static class Builder {

        private final AlertConfiguration configuration;

        public Builder() {
            configuration = new AlertConfiguration();
        }

        private Builder(AlertConfiguration configuration) {
            this.configuration = new AlertConfiguration(configuration);
        }

        public AlertConfiguration build() {
            return new AlertConfiguration(configuration);
        }

        public Builder withTitle(String title) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.title = title;
            return new Builder(newConfiguration);
        }

        public Builder withMessage(String message) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.message = message;
            return new Builder(newConfiguration);
        }

        public Builder withDuration(@Duration int duration) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.duration = duration;
            return new Builder(newConfiguration);
        }

        public Builder withActionText(String actionText) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.actionText = actionText;
            return new Builder(newConfiguration);
        }

        public Builder withCancelText(String cancelText) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.cancelText = cancelText;
            return new Builder(newConfiguration);
        }

        public Builder withTitle(Context context, @StringRes int titleResId) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.title = context.getString(titleResId);
            return new Builder(newConfiguration);
        }

        public Builder withMessage(Context context, @StringRes int messageResId) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.message = context.getString(messageResId);
            return new Builder(newConfiguration);
        }

        public Builder withActionText(Context context, @StringRes int actionTextResId) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.actionText = context.getString(actionTextResId);
            return new Builder(newConfiguration);
        }

        public Builder withCancelText(Context context, @StringRes int cancelTextResId) {
            AlertConfiguration newConfiguration = new AlertConfiguration(configuration);
            newConfiguration.cancelText = context.getString(cancelTextResId);
            return new Builder(newConfiguration);
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
