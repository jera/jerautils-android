package br.com.jera.example

import android.app.Application
import br.com.jera.jerautils.placeholders.Placeholders

/**
 * Created by daividsilverio on 29/11/16.
 */
class ExampleApplication : Application() {
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
    }
}