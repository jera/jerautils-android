package br.com.jera.example.placeholders

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import br.com.jera.example.R
import br.com.jera.jerautils.placeholders.Placeholders
import br.com.jera.jerautils.placeholders.PlaceholdersManager
import br.com.jera.jerautils.placeholders.impl.SimplePlaceholder
import br.com.jera.jerautils.placeholders.interfaces.ConfigurablePlaceholder

/**
 * Created by daividsilverio on 29/11/16.
 */
class PlaceholdersExample : AppCompatActivity() {

    companion object {
        val CUSTOM_LOADING = "CUSTOM_LOADING"
    }

    private lateinit var placeholdersManager: PlaceholdersManager<ConfigurablePlaceholder>
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholders)
        configButtons()
        placeholdersManager = Placeholders.buildDefaultPlaceholdersManager(this)
        placeholdersManager.add(CUSTOM_LOADING, SimplePlaceholder(window.decorView, R.id.stub_custom_loading))
    }

    private fun configButtons() {
        findViewById(R.id.simple_placeholder_load).setOnClickListener { doHeavyStuff(false, Placeholders.LOADING) }
        findViewById(R.id.simple_placeholder_button).setOnClickListener { doHeavyStuff(true, Placeholders.ERROR) }
        findViewById(R.id.image_placeholder_button).setOnClickListener { doHeavyStuff(true, Placeholders.ERROR_WITH_ICON) }
        findViewById(R.id.custom_placeholder_button).setOnClickListener { doHeavyStuff(false, CUSTOM_LOADING) }
    }


    private fun doHeavyStuff(shouldFail: Boolean, placeholder: String) {
        if (isRunning) {
            return
        } else {
            isRunning = true
            if (placeholder == CUSTOM_LOADING) {
                placeholdersManager.show(CUSTOM_LOADING)
            } else {
                placeholdersManager.show(Placeholders.LOADING).configure("Doing Heavy Stuff!")
            }
            Handler().postDelayed({
                processHeavyResults(shouldFail, placeholder)
                isRunning = false
            }, 1500)
        }
    }

    private fun processHeavyResults(shouldFail: Boolean, placeholder: String) {
        if (shouldFail) {
            when (placeholder) {
                Placeholders.ERROR -> placeholdersManager.show(Placeholders.ERROR)
                        .configure("Simple Placeholder", "Try Again Bro", { doHeavyStuff(true, Placeholders.ERROR) })
                Placeholders.ERROR_WITH_ICON -> placeholdersManager.show(Placeholders.ERROR_WITH_ICON)
                        .configure("Placeholder with Icon", "Try Again Bro", { doHeavyStuff(true, Placeholders.ERROR_WITH_ICON) }, R.drawable.ic_announcement_black_48dp)
            }
            placeholdersManager.show(placeholder)
        } else {
            placeholdersManager.hideAll()
        }
    }
}