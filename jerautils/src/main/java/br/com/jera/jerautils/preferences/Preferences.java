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

    private static final String PREFERENCES_NAME = "APP_PREFERENCES";
    private static Preferences instance;
    private final ContextProvider contextProvider;
    private final JsonConverter converter;

    private Preferences(ContextProvider context, JsonConverter converter) {
        this.contextProvider = context;
        this.converter = converter;
    }

    private static SharedPreferences getPreferences() {
        if (instance == null) {
            throw new RuntimeException("Did you initialize the Preference Utils?");
        }
        return instance.contextProvider.getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static JsonConverter getJsonConverter() {
        if (instance == null) {
            throw new RuntimeException(("Did you initialize the Preference Utils?"));
        }
        return instance.converter;
    }


    public static void putObject(String key, Object value) {
        getPreferences().edit().putString(key, getJsonConverter().toJson(value)).commit();
    }

    public static <T> T get(String key, Class<T> clazz) {
        return getJsonConverter().fromJson(getPreferences().getString(key, null), clazz);
    }

    public static <T> T get(String key, Type type) {
        return getJsonConverter().fromJson(getPreferences().getString(key, null), type);
    }

    public static boolean contains(String key) {
        return getPreferences().contains(key);
    }

    public static void remove(String key) {
        getPreferences().edit().remove(key).commit();
    }

    public static void putString(String key, @Nullable String string) {
        getPreferences().edit().putString(key, string).commit();
    }

    public static void putStringSet(String key, @Nullable Set<String> stringSet) {
        getPreferences().edit().putStringSet(key, stringSet).commit();
    }

    public static void putInt(String key, int anInt) {
        getPreferences().edit().putInt(key, anInt).commit();
    }

    public static void putLong(String key, long anLong) {
        getPreferences().edit().putLong(key, anLong).commit();
    }

    public static void putFloat(String key, float aFloat) {
        getPreferences().edit().putFloat(key, aFloat).commit();
    }

    public static void putBoolean(String key, boolean aBoolean) {
        getPreferences().edit().putBoolean(key, aBoolean).commit();
    }

    @NonNull
    public static String getString(String key, String defaultString) {
        return getPreferences().getString(key, defaultString);
    }

    @NonNull
    public static Set<String> getStringSet(String key, Set<String> defaultStringSet) {
        return getPreferences().getStringSet(key, defaultStringSet);
    }

    public static int getInt(String key, int defaultInt) {
        return getPreferences().getInt(key, defaultInt);
    }

    public static long getLong(String key, long defaultLong) {
        return getPreferences().getLong(key, defaultLong);
    }

    public static float getFloat(String key, float defaultFloat) {
        return getPreferences().getFloat(key, defaultFloat);
    }

    public static boolean getBoolean(String key, boolean defaultBoolean) {
        return getPreferences().getBoolean(key, defaultBoolean);
    }

    @Nullable
    public static String getString(String key) {
        return getPreferences().getString(key, null);
    }

    @Nullable
    public static Set<String> getStringSet(String key) {
        return getPreferences().getStringSet(key, null);
    }

    @Nullable
    public static Integer getInt(String key) {
        if (contains(key)) {
            return getPreferences().getInt(key, 0);
        }
        return null;
    }

    @Nullable
    public static Long getLong(String key) {
        if (getPreferences().contains(key)) {
            return getPreferences().getLong(key, 0);
        }
        return null;
    }

    @Nullable
    public static Float getFloat(String key) {
        if (getPreferences().contains(key)) {
            return getPreferences().getFloat(key, 0);
        }
        return null;
    }

    @Nullable
    public static Boolean getBoolean(String key) {
        if (getPreferences().contains(key)) {
            return getPreferences().getBoolean(key, false);
        }
        return null;
    }

    public static void init(ContextProvider contextProvider, JsonConverter converter) {
        if (contextProvider == null || converter == null) {
            throw new RuntimeException("Dammnn Son! Why!?? Just, Why!?");
        }
        instance = new Preferences(contextProvider, converter);
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
