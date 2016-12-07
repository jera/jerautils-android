package br.com.jera.example.dialogs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.jera.example.R
import br.com.jera.jerautils.alerts.AlertConfiguration
import br.com.jera.jerautils.alerts.Alerts
import br.com.jera.jerautils.common.ConfirmationCallback

/**
 * Created by daividsilverio on 29/11/16.
 */
class DialogsExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)

        val alertConfiguration = AlertConfiguration.Builder()
                .withTitle("Alerta de Voo")
                .withMessage("Você irá voar em breve")
                .withActionText("Damn")
                .withCancelText("Noo")
                .withDuration(AlertConfiguration.LENGTH_SHORT)

        val confirmationCallback = object : ConfirmationCallback {
            override fun onConfirm() = Alerts.toast(this@DialogsExample, "Action Button Clicked!")

            override fun onRefuse() = Alerts.toast(this@DialogsExample, "Refuse Clicked!")
        }

        findViewById(R.id.confirmation_dialog_button).setOnClickListener {
            Alerts.showDialog(this, alertConfiguration
                    .withCancelText(null)
                    .build(),
                    confirmationCallback)
        }

        findViewById(R.id.cancelable_dialog_button).setOnClickListener {
            Alerts.showDialog(this, alertConfiguration
                    .build(),
                    confirmationCallback)
        }

        findViewById(R.id.plain_snack).setOnClickListener {
            Alerts.showSnackBar(findViewById(R.id.root), alertConfiguration
                    .withActionText(null)
                    .build(),
                    null)
        }

        findViewById(R.id.action_snack).setOnClickListener {
            Alerts.showSnackBar(findViewById(R.id.root), alertConfiguration
                    .withActionText("Stooppp!")
                    .build(),
                    confirmationCallback
            )
        }
    }
}