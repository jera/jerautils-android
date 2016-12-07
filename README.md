# JeraUtils - Android
JeraUtils for Android is a collection of simple utils created to help reduce the amount of
boilerplate during our daily app development, such as placeholders for errors, screen's loadings,
alerts and preferences.

## How to Install
Add this repository (Temporary) to your root build.gradle
```groovy
repositories {
        maven {
            url "http://dl.bintray.com/daividssilverio/jerautils-android"
        }
    }
```

Add this to your dependencies
```groovy
compile 'br.com.jera.jerautils-android:jerautils-android:0.1.0'
```

## Dependencies
```groovy
compile "com.android.support:design:x.y.z"
```

## Features

### Preferences
    Init Preferences once with a ContextProvider and a JsonConverter in
    your application, activity, whatever:
```java
    Preferences.init(new Preferences.ContextProvider() {
                @Override
                public Context getContext() {
                    return this; //supposing you are inside your application class
                }
            }, new Preferences.JsonConverter() {
                @Override
                public String toJson(Object obj) {
                    return gson.toJson(obj);
                }

                @Override
                public <T> T fromJson(@NonNull String obj, @NonNull Class<T> clazz) {
                    return gson.fromJson(obj, clazz);
                }

                @Override
                public <T> T fromJson(@NonNull String obj, @NonNull Type type) {
                    return gson.fromJson(obj, type);
                }
            });
```

    Then use it as you would regularly, but with the Preferences Facade:
```java
    Preferences.putBoolean("BOOL_KEY", true);
    Preferences.putFloat("FLOAT_KEY", 0.4f);
    Preferences.putInt("INT_KEY", 1);
    Preferences.putLong("LONG_KEY", 10);
    Preferences.putString("STRING_KEY", "String");
    Preferences.putObject("SERIALIZABLE_OBJECT", new Date());
```


### Dialogs, Toasts, Snackbars

### Pagination