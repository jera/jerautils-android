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

    private static int defaultLoadingViewId, defaultLoadingMessageTextViewId, defaultErrorViewId, defaultErrorMessageTextViewId, defaultErrorButtonId, defaultErrorIconId;

    public static void init(int defaultLoadingViewId, int defaultLoadingMessageTextViewId, int defaultErrorViewId, int defaultErrorMessageTextViewId, int defaultErrorButtonId, int defaultErrorIconId) {
        Placeholders.defaultLoadingViewId = defaultLoadingViewId;
        Placeholders.defaultLoadingMessageTextViewId = defaultLoadingMessageTextViewId;
        Placeholders.defaultErrorViewId = defaultErrorViewId;
        Placeholders.defaultErrorMessageTextViewId = defaultErrorMessageTextViewId;
        Placeholders.defaultErrorButtonId = defaultErrorButtonId;
        Placeholders.defaultErrorIconId = defaultErrorIconId;
    }

    public static PlaceholdersManager<ConfigurablePlaceholder> buildDefaultPlaceholdersManager(Activity activity) {
        return buildDefaultPlaceholdersManager(activity.getWindow().getDecorView());
    }

    public static PlaceholdersManager<ConfigurablePlaceholder> buildDefaultPlaceholdersManager(View contentView) {
        return new PlaceholdersManager<ConfigurablePlaceholder>()
                .add(LOADING, new SimplePlaceholder(contentView, defaultLoadingViewId, defaultLoadingMessageTextViewId))
                .add(ERROR, new PlaceholderWithImage(contentView, defaultErrorViewId, defaultErrorMessageTextViewId, defaultErrorButtonId, defaultErrorIconId));
    }

    public static View findChildOrBust(View view, int childViewId) {
        View childView = view.findViewById(childViewId);
        if (childView == null) {
            throw new RuntimeException("Could not find view with id " + childViewId);
        }
        return childView;
    }
}
