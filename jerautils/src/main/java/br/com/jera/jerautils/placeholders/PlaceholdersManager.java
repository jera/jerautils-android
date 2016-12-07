package br.com.jera.jerautils.placeholders;

import android.view.View;

import java.util.HashMap;

import br.com.jera.jerautils.placeholders.interfaces.Placeholder;

/**
 * Created by helio on 18/04/16.
 */
public class PlaceholdersManager<T extends Placeholder> {

    private HashMap<String, T> placeholders;

    public PlaceholdersManager() {
        this.placeholders = new HashMap<>();
    }

    public PlaceholdersManager<T> add(String placeholderName, T placeholder) {
        if (placeholderName == null || placeholder == null) {
            throw new RuntimeException("Dude, don't use null params, srsly.");
        }
        placeholders.put(placeholderName, placeholder);
        return this;
    }

    public T show(String placeholderName) {
        hideAll();
        T placeholder = placeholders.get(placeholderName);
        placeholder.getView().setVisibility(View.VISIBLE);
        return placeholder;
    }

    public void hideAll() {
        for (T placeholder : placeholders.values()) {
            if (placeholder.isViewInflated()) {
                placeholder.getView().setVisibility(View.GONE);
            }
        }
    }
}
