# JeraUtils - Android
JeraUtils for Android is a collection of simple utils created to help reduce the amount of
boilerplate during our daily app development, such as placeholders for errors, screen's loadings,
alerts and preferences.

## How to Install
Add this repository (Temporary) to your root build.gradle
```groovy
repositories {
        maven {
            url "http://dl.bintray.com/daividssilverio/maven"
        }
    }
```

Add this to your dependencies
```groovy
compile 'br.com.jera.jerautils-android:jerautils-android:latest-version'
```

## Dependencies

If you use Snackbars:

```groovy
compile "com.android.support:design:x.y.z"
```

## Features
* [Preferences](#preferences)
* [Dialogs, Toasts, Snackbars](#alerts)
* [Pagination](#pagination)
* [Placeholders](#placeholders)

### <a id="preferences"></a> Preferences
Init Preferences once with a ContextProvider and a JsonConverter in
your application, activity, whatever:
```groovy
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
```groovy
    Preferences.putBoolean("BOOL_KEY", true);
    Preferences.putFloat("FLOAT_KEY", 0.4f);
    Preferences.putInt("INT_KEY", 1);
    Preferences.putLong("LONG_KEY", 10);
    Preferences.putString("STRING_KEY", "String");
    Preferences.putObject("SERIALIZABLE_OBJECT", new Date());
```


### <a id="alerts"></a>Dialogs, Toasts, Snackbars
Use the ```Alerts``` facade to create Dialogs, Snackbars and Toasts together with
an ```AlertConfiguration``` a ```ConfirmationCallback```
```AlertConfiguration.Builder``` is immutable and can be used to create default Configurations to
use throughout the code

Create an AlertConfiguration.Builder instance:
```groovy
AlertConfiguration.Builder builder = AlertConfiguration.Builder()
                .withTitle("Alerta de Voo")
                .withMessage("Você irá voar em breve")
                .withActionText("Damn")
                .withCancelText("Noo")
                .withDuration(AlertConfiguration.LENGTH_SHORT)
```

Then use it with the Facades:

To create a dialog without the cancellation button:
```groovy
Alerts.showDialog(context, builder
                    .withCancelText(null)
                    .build(),
                    new NotificationCallback() {
                                @Override
                                public void onConfirm() {
                                     //todo: do something
                                }
                            });
```

To create a dialog with the cancellation button:
```groovy
Alerts.showDialog(context, builder.build(),
                    new ConfirmationCallback() {
                                @Override
                                public void onConfirm() {
                                    //todo: do something
                                }

                                @Override
                                public void onRefuse() {
                                    //todo: do something
                                }
                            });
```


<
To create a simple snackbar, without an action button:
```groovy
Alerts.showSnackBar(findViewById(R.id.root), builder
                    .withActionText(null)
                    .build(), null);
```

To create a snackbar with the action button:
```groovy
Alerts.showSnackBar(findViewById(R.id.root), builder
                    .withActionText("Stooppp!")
                    .build(),
                    new NotificationCallback() {
                        @Override
                        public void onConfirm() {
                            //todo: do something
                        }
                     });
```

### <a id="placeholders"></a> Placeholders
You can setup layouts with default placeholders to use throughout your application
like this:
```groovy
Placeholders.Configuration.Builder builder = Placeholders.Configuration.Builder()
Placeholders.init(builder.withDefaultLoadingViewStubId(R.id.stub_loading)
        .withDefaultErrorViewStubId(R.id.stub_error)
        .withDefaultErrorWithIconViewStubId(R.id.stub_error_with_image)
        .withDefaultLoadingMessageTextViewId(R.id.text_loading_message)
        .withDefaultErrorMessageTextViewId(R.id.text_error_message)
        .withDefaultErrorButtonId(R.id.button_error_action)
        .withDefaultErrorIconId(R.id.image_error_icon)
        .build());
```

Then you can use it in your View or Activity, containing the corresponding placeholders
ids you specified on the Configuration.

Use the ```PlaceholdersManager``` to handle your placeholders:
```groovy
placeholdersManager = Placeholders.buildDefaultPlaceholdersManager(this);
placeholdersManager.add(CUSTOM_LOADING, SimplePlaceholder(window.decorView, R.id.stub_custom_loading));
```

After that, you can use the placeholdersManager to show, hide and configure your placeholders
as you need:
```groovy
placeholdersManager.show(CUSTOM_LOADING);
placeholdersManager.show(Placeholders.LOADING).configure("Doing Heavy Stuff!");
placeholdersManager.show(Placeholders.ERROR)
                   .configure("Simple Placeholder", "Try Again Bro", { doHeavyStuff() });
placeholdersManager.hideAll();
```
Activity Layout excerpt Example:
```xml
<FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="A lot of beuatiful Content Here!" />

        <ViewStub
            android:id="@+id/stub_custom_loading"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout="@layout/view_custom_loading"
            />

        <ViewStub
            android:id="@+id/stub_error"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout="@layout/view_error" />

        <ViewStub
            android:id="@+id/stub_error_with_image"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout="@layout/view_error_with_image" />

        <ViewStub
            android:id="@+id/stub_loading"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout="@layout/view_loading" />

    </FrameLayout>
```
Check the [sample](https://github.com/jera/jerautils-android/blob/develop/sample/src/main/java/br/com/jera/example/placeholders/PlaceholdersExample.kt) to see how it is used:

### <a id="pagination"></a> Pagination
Use the ```Paginator``` helper together with a ```DataSource``` to handle
paginated requests to populate a recycler view when it scrolls

```groovy
Paginator paginator = Paginator.with(YourDataSource())
                .fromPage(1)
                .withPageSize(20)
                .over(recyclerView)
                .start()
```

You can get information about the current status of the pagination with
the instance of the Paginator if you need to.