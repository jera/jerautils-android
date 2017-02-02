package br.com.jera.example

import android.app.Application
import br.com.jera.jerautils.placeholders.Placeholders
import br.com.jera.jerautils.preferences.Preferences
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by daividsilverio on 29/11/16.
 */
class ExampleApplication : Application() {

    companion object {
        val gson = Gson()
        private val APP_PREFERENCES = "APP_PREFERENCES"
    }

    override fun onCreate() {
        super.onCreate()
        val builder = Placeholders.Configuration.Builder()
        Placeholders.init(builder.withDefaultLoadingViewStubId(R.id.stub_loading)
                .withDefaultErrorViewStubId(R.id.stub_error)
                .withDefaultErrorWithIconViewStubId(R.id.stub_error_with_image)
                .withDefaultLoadingMessageTextViewId(R.id.text_loading_message)
                .withDefaultErrorMessageTextViewId(R.id.text_error_message)
                .withDefaultErrorButtonId(R.id.button_error_action)
                .withDefaultErrorIconId(R.id.image_error_icon)
                .build())

        Preferences.initDefaultInstance(Preferences(APP_PREFERENCES, { this@ExampleApplication },
                object : Preferences.JsonConverter {
                    override fun <T : Any?> fromJson(obj: String, type: Type): T {
                        return ExampleApplication.gson.fromJson<T>(obj, type)
                    }

                    override fun toJson(obj: Any?): String {
                        return ExampleApplication.gson.toJson(obj)
                    }

                    override fun <T : Any?> fromJson(obj: String, clazz: Class<T>): T {
                        return ExampleApplication.gson.fromJson<T>(obj, clazz)
                    }
                })
        )
    }
}