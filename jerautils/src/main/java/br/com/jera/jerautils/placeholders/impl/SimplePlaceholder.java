
package br.com.jera.jerautils.placeholders.impl;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.jera.jerautils.placeholders.interfaces.ConfigurablePlaceholder;
import br.com.jera.jerautils.placeholders.Placeholders;

/**
 * Created by helio on 19/04/16.
 */
public class SimplePlaceholder extends ViewStubPlaceholder implements ConfigurablePlaceholder {

    private int messageTextViewId, buttonId;
    private TextView messageTextView;
    private Button button;

    public SimplePlaceholder(View contentView, int viewId) {
        this(contentView, viewId, 0, 0);
    }

    public SimplePlaceholder(View contentView, int viewId, int messageTextViewId) {
        this(contentView, viewId, messageTextViewId, 0);
    }

    public SimplePlaceholder(View contentView, int viewId, int messageTextViewId, int buttonId) {
        super(contentView, viewId);
        this.messageTextViewId = messageTextViewId;
        this.buttonId = buttonId;
    }

    @Override
    public void clearConfiguration() {
        if (messageTextViewId != 0) configureMessageTextView(null);
        if (buttonId != 0) configureButton(null, null);
    }

    @Override
    public void configure(String message) {
        configureMessageTextView(message);
        if (buttonId != 0) configureButton(null, null);
    }

    @Override
    public void configure(String message, String buttonText, View.OnClickListener buttonCLickListener) {
        configureMessageTextView(message);
        configureButton(buttonText, buttonCLickListener);
    }

    @Override
    public void configure(String message, String buttonText, View.OnClickListener buttonClickListener, int imageIconId) {
        throw new RuntimeException("The simple placeholder doesn't have a Image! use PlaceHolderWithImage Instead");
    }

    protected void configureMessageTextView(String message) {
        try {
            getMessageTextView().setText(message);
            getMessageTextView().setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
        } catch (RuntimeException e) {
            throwConfigurationError("messageTextView", messageTextViewId, e);
        }
    }

    protected void configureButton(String text, View.OnClickListener onClickListener) {
        try {
            getButton().setText(text);
            getButton().setOnClickListener(onClickListener);
            getButton().setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        } catch (RuntimeException e) {
            throwConfigurationError("button", buttonId, e);
        }
    }

    protected void throwConfigurationError(String viewName, int viewId, Exception exception) {
        if (viewId == 0) {
            throw new RuntimeException("Cannot configure " + viewName + ". The required id was not provided on the constructor!", exception);
        } else {
            throw new RuntimeException("Cannot configure " + viewName + ". The provided id was not found in the placeholder view hierarchy!", exception);
        }
    }

    public TextView getMessageTextView() {
        if (messageTextView == null) {
            messageTextView = (TextView) Placeholders.findChildOrBust(getView(), messageTextViewId);
        }
        return messageTextView;
    }

    public Button getButton() {
        if (button == null) {
            button = (Button) Placeholders.findChildOrBust(getView(), buttonId);
        }
        return button;
    }
}
