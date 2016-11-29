package br.com.jera.jerautils.placeholders.interfaces;

import android.view.View;

/**
 * Created by daividsilverio on 28/11/16.
 */

public interface ConfigurablePlaceholder extends Placeholder {
    void clearConfiguration();

    void configure(String message);

    void configure(String message, String buttonText, View.OnClickListener buttonClickListener);

    void configure(String message, String buttonText, View.OnClickListener buttonClickListener, int imageIconId);
}
