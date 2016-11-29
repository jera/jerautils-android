package br.com.jera.jerautils.placeholders;

import android.app.Activity;
import android.view.View;

import br.com.jera.jerautils.placeholders.impl.PlaceholderWithImage;
import br.com.jera.jerautils.placeholders.impl.SimplePlaceholder;
import br.com.jera.jerautils.placeholders.interfaces.ConfigurablePlaceholder;

/**
 * Created by helio on 18/04/16.
 */
public class Placeholders {

    public static String LOADING = "LOADING";
    public static String ERROR = "ERROR";
    public static String ERROR_WITH_ICON = "ERROR_WITH_ICON";

    private static int defaultLoadingViewStubId, defaultErrorWithIconViewStubId, defaultLoadingMessageTextViewId, defaultErrorViewStubId, defaultErrorMessageTextViewId, defaultErrorButtonId, defaultErrorIconId;

    public static void init(Configuration configuration) {
        Placeholders.defaultLoadingViewStubId = configuration.defaultLoadingViewStubId;
        Placeholders.defaultErrorViewStubId = configuration.defaultErrorViewStubId;
        Placeholders.defaultErrorWithIconViewStubId = configuration.defaultErrorWithIconViewStubId;
        Placeholders.defaultLoadingMessageTextViewId = configuration.defaultLoadingMessageTextViewId;
        Placeholders.defaultErrorMessageTextViewId = configuration.defaultErrorMessageTextViewId;
        Placeholders.defaultErrorButtonId = configuration.defaultErrorButtonId;
        Placeholders.defaultErrorIconId = configuration.defaultErrorIconId;
    }

    public static PlaceholdersManager<ConfigurablePlaceholder> buildDefaultPlaceholdersManager(Activity activity) {
        return buildDefaultPlaceholdersManager(activity.getWindow().getDecorView());
    }

    public static PlaceholdersManager<ConfigurablePlaceholder> buildDefaultPlaceholdersManager(View contentView) {
        return new PlaceholdersManager<ConfigurablePlaceholder>()
                .add(LOADING, new SimplePlaceholder(contentView, defaultLoadingViewStubId, defaultLoadingMessageTextViewId))
                .add(ERROR, new SimplePlaceholder(contentView, defaultErrorViewStubId, defaultErrorMessageTextViewId, defaultErrorButtonId))
                .add(ERROR_WITH_ICON, new PlaceholderWithImage(contentView, defaultErrorWithIconViewStubId, defaultErrorMessageTextViewId, defaultErrorButtonId, defaultErrorIconId));
    }

    public static View findChildOrBust(View view, int childViewId) {
        View childView = view.findViewById(childViewId);
        if (childView == null) {
            throw new RuntimeException("Could not find view with id " + childViewId);
        }
        return childView;
    }

    public static class Configuration {
        private int defaultLoadingViewStubId, defaultErrorWithIconViewStubId, defaultLoadingMessageTextViewId, defaultErrorViewStubId, defaultErrorMessageTextViewId, defaultErrorButtonId, defaultErrorIconId;

        private Configuration() {
        }

        public static class Builder {

            private final Configuration configuration;

            public Builder() {
                this.configuration = new Configuration();
            }

            public Configuration build() {
                return configuration;
            }

            public Builder withDefaultLoadingViewStubId(int defaultLoadingViewId) {
                configuration.defaultLoadingViewStubId = defaultLoadingViewId;
                return this;
            }

            public Builder withDefaultLoadingMessageTextViewId(int defaultLoadingMessageTextViewId) {
                configuration.defaultLoadingMessageTextViewId = defaultLoadingMessageTextViewId;
                return this;
            }

            public Builder withDefaultErrorWithIconViewStubId(int defaultErrorWithIconViewStubId) {
                configuration.defaultErrorWithIconViewStubId = defaultErrorWithIconViewStubId;
                return this;
            }

            public Builder withDefaultErrorViewStubId(int defaultErrorViewId) {
                configuration.defaultErrorViewStubId = defaultErrorViewId;
                return this;
            }

            public Builder withDefaultErrorMessageTextViewId(int defaultErrorMessageTextViewId) {
                configuration.defaultErrorMessageTextViewId = defaultErrorMessageTextViewId;
                return this;
            }

            public Builder withDefaultErrorButtonId(int defaultErrorButtonId) {
                configuration.defaultErrorButtonId = defaultErrorButtonId;
                return this;
            }

            public Builder withDefaultErrorIconId(int defaultErrorIconId) {
                configuration.defaultErrorIconId = defaultErrorIconId;
                return this;
            }
        }
    }

}
