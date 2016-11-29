package br.com.jera.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.jera.example.dialogs.DialogsExample
import br.com.jera.example.placeholders.PlaceholdersExample

class MainActivity : AppCompatActivity() {
    companion object {
        val examples = listOf<Pair<String, Class<out Activity>>>(
                "Placeholders" to PlaceholdersExample::class.java,
                "Dialogs" to DialogsExample::class.java
        )
    }

    lateinit var itemsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemsList = findViewById(R.id.items_list) as RecyclerView
        setupExamplesList()
    }

    private fun setupExamplesList() {
        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ExamplesAdapter(examples, { openExample(it) })
    }

    private fun openExample(activityClass: Class<out Activity>) {
        startActivity(Intent(this, activityClass))
    }
}

