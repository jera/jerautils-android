package br.com.jera.jerautils.placeholders.impl;

import android.view.View;
import android.widget.ImageView;

import br.com.jera.jerautils.placeholders.Placeholders;

/**
 * Created by daividsilverio on 28/11/16.
 */

public class PlaceholderWithImage extends SimplePlaceholder {
    private int imageViewId;
    private ImageView imageView;

    public PlaceholderWithImage(View contentView, int viewId, int messageTextViewId, int buttonId, int imageViewId) {
        super(contentView, viewId, messageTextViewId, buttonId);
        this.imageViewId = imageViewId;
    }

    @Override
    public void clearConfiguration() {
        super.clearConfiguration();
        if (imageViewId != 0) configureImageView(0);
    }

    @Override
    public void configure(String message) {
        super.configure(message);
        if (imageViewId != 0) getImageView().setVisibility(View.GONE);
    }

    @Override
    public void configure(String message, String buttonText, View.OnClickListener buttonCLickListener, int imageIconId) {
        configureMessageTextView(message);
        configureButton(buttonText, buttonCLickListener);
        configureImageView(imageIconId);
    }

    private void configureImageView(int imageIconId) {
        try {
            getImageView().setImageResource(imageIconId);
        } catch (RuntimeException e) {
            throwConfigurationError("imageView", imageViewId, e);
        }
    }

    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) Placeholders.findChildOrBust(getView(), imageViewId);
        }
        return imageView;
    }
}
