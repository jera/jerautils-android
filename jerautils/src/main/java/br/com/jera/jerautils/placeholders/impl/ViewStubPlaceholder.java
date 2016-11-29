package br.com.jera.jerautils.placeholders.impl;

import android.view.View;
import android.view.ViewStub;

import br.com.jera.jerautils.placeholders.interfaces.Placeholder;
import br.com.jera.jerautils.placeholders.Placeholders;

/**
 * Created by helio on 19/04/16.
 */
public class ViewStubPlaceholder implements Placeholder {

    private ViewStub viewStub;
    private View view;

    public ViewStubPlaceholder(View contentView, int viewId) {
        View view = Placeholders.findChildOrBust(contentView, viewId);
        if (view instanceof ViewStub) {
            this.viewStub = (ViewStub) view;
        } else {
            this.view = view;
        }
    }

    @Override
    public boolean isViewInflated() {
        return this.view != null;
    }

    @Override
    public View getView() {
        if (this.view == null) {
            this.view = this.viewStub.inflate();
        }
        return this.view;
    }
}
