package br.com.jera.example.preferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import br.com.jera.example.R
import br.com.jera.jerautils.preferences.Preferences
import com.google.gson.reflect.TypeToken

class PreferencesExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences_example)
        setObjects()
        showValues()
    }

    private fun showValues() {
        with(findViewById(R.id.boolId) as TextView) {
            text = Preferences.getBoolean("bool").toString()
        }
        with(findViewById(R.id.floatId) as TextView) {
            text = Preferences.getFloat("float").toString()
        }
        with(findViewById(R.id.intId) as TextView) {
            text = Preferences.getInt("int").toString()
        }
        with(findViewById(R.id.longId) as TextView) {
            text = Preferences.getLong("long").toString()
        }
        with(findViewById(R.id.stringId) as TextView) {
            text = Preferences.getString("string")
        }
        with(findViewById(R.id.stringsetId) as TextView) {
            text = Preferences.getStringSet("string-set").toString()
        }
        with(findViewById(R.id.collectorId) as TextView) {
            text = Preferences.get("collector", Collector::class.java).name
        }

        var typeToken = genericType<List<Thing>>()
        with(findViewById(R.id.listId) as TextView) {
            var things: List<Thing> = Preferences.get("list", typeToken)
            text = things.toString()
        }
    }


    private fun setObjects() {
        Preferences.putBoolean("bool", true)
        Preferences.putFloat("float", 0.4f)
        Preferences.putInt("int", 1)
        Preferences.putLong("long", 10)
        Preferences.putString("string", "String")
        Preferences.putStringSet("string-set", setOf("one", "two", "three"))
        Preferences.putObject("collector",
                Collector("juca", listOf(
                        Thing("chair", "to sit"),
                        Thing("hat", "to wear")
                ))
        )
        Preferences.putObject("list", listOf(
                Thing("beard", "to show off"),
                Thing("cup", "to put coffee"),
                Thing("beer", "to drink")
        ))
    }

    data class Thing(val name: String, val use: String)
    data class Collector(val name: String, val things: List<Thing>)

    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type
}
