package br.com.jera.jerautils.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by daividsilverio on 28/11/16.
 */

public class Preferences {

    private final ContextProvider contextProvider;
    private final JsonConverter converter;
    private final String preferencesName;

    private static Preferences defaultInstance;

    public Preferences(String preferencesName, ContextProvider contextProvider, JsonConverter jsonConverter) {
        this.preferencesName = preferencesName;
        this.contextProvider = contextProvider;
        this.converter = jsonConverter;
    }

    public static void initDefaultInstance(Preferences preferences) {
        defaultInstance = preferences;
    }

    public static Preferences getDefaultInstance() {
        if (defaultInstance == null) {
            throw new RuntimeException("You have to instantiate the default instance before using it. Use initDefaultInstance(Preferences) for it.");
        }
        return defaultInstance;
    }

    private SharedPreferences getPreferences() {
        return contextProvider.getContext().getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    private JsonConverter getJsonConverter() {
        return converter;
    }


    public void putObject(String key, Object value) {
        getPreferences().edit().putString(key, getJsonConverter().toJson(value)).commit();
    }

    public <T> T get(String key, Class<T> clazz) {
        return getJsonConverter().fromJson(getPreferences().getString(key, null), clazz);
    }

    public <T> T get(String key, Type type) {
        return getJsonConverter().fromJson(getPreferences().getString(key, null), type);
    }

    public boolean contains(String key) {
        return getPreferences().contains(key);
    }

    public void remove(String key) {
        getPreferences().edit().remove(key).commit();
    }

    public void putString(String key, @Nullable String string) {
        getPreferences().edit().putString(key, string).commit();
    }

    public void putStringSet(String key, @Nullable Set<String> stringSet) {
        getPreferences().edit().putStringSet(key, stringSet).commit();
    }

    public void putInt(String key, int anInt) {
        getPreferences().edit().putInt(key, anInt).commit();
    }

    public void putLong(String key, long anLong) {
        getPreferences().edit().putLong(key, anLong).commit();
    }

    public void putFloat(String key, float aFloat) {
        getPreferences().edit().putFloat(key, aFloat).commit();
    }

    public void putBoolean(String key, boolean aBoolean) {
        getPreferences().edit().putBoolean(key, aBoolean).commit();
    }

    @NonNull
    public String getString(String key, String defaultString) {
        return getPreferences().getString(key, defaultString);
    }

    @NonNull
    public Set<String> getStringSet(String key, Set<String> defaultStringSet) {
        return getPreferences().getStringSet(key, defaultStringSet);
    }

    public int getInt(String key, int defaultInt) {
        return getPreferences().getInt(key, defaultInt);
    }

    public long getLong(String key, long defaultLong) {
        return getPreferences().getLong(key, defaultLong);
    }

    public float getFloat(String key, float defaultFloat) {
        return getPreferences().getFloat(key, defaultFloat);
    }

    public boolean getBoolean(String key, boolean defaultBoolean) {
        return getPreferences().getBoolean(key, defaultBoolean);
    }

    @Nullable
    public String getString(String key) {
        return getPreferences().getString(key, null);
    }

    @Nullable
    public Set<String> getStringSet(String key) {
        return getPreferences().getStringSet(key, null);
    }

    @Nullable
    public Integer getInt(String key) {
        if (contains(key)) {
            return getPreferences().getInt(key, 0);
        }
        return null;
    }

    @Nullable
    public Long getLong(String key) {
        if (getPreferences().contains(key)) {
            return getPreferences().getLong(key, 0);
        }
        return null;
    }

    @Nullable
    public Float getFloat(String key) {
        if (getPreferences().contains(key)) {
            return getPreferences().getFloat(key, 0);
        }
        return null;
    }

    @Nullable
    public Boolean getBoolean(String key) {
        if (getPreferences().contains(key)) {
            return getPreferences().getBoolean(key, false);
        }
        return null;
    }

    public interface JsonConverter {
        String toJson(Object obj);

        <T> T fromJson(@NonNull String obj, @NonNull Class<T> clazz);

        <T> T fromJson(@NonNull String obj, @NonNull Type type);
    }

    public interface ContextProvider {
        Context getContext();
    }
}
