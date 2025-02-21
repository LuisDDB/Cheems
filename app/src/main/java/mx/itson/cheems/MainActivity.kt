package mx.itson.cheems

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var gameOverCard =0
    var flipedCards = ArrayList<View>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        start()

        Log.d("El valor de la carta","La carta perdedora es ${gameOverCard.toString()}")
    }
    fun start(){
        for(i in 1..6){
            val btnCard= findViewById<View>(
                resources.getIdentifier("card$i", "id", this.packageName)
            ) as ImageButton
            btnCard.setOnClickListener(this)
            btnCard.setBackgroundResource(R.drawable.icon_pregunta)
        }
        val btnRestart = findViewById<View>(R.id.btn_restart) as Button
        btnRestart.setOnClickListener(this)
        gameOverCard = (1..6).random()
    }
    fun restart(){
        flipAll(R.drawable.icon_pregunta)
        gameOverCard = (1..6).random()
        flipedCards = ArrayList<View>()
    }
    fun win(flipedCard: View){
        //Si la lista esta vacia
        if(flipedCards.isEmpty()){
            flipedCards.add(flipedCard)
            return
        }
        //Si la lista contiene el objeto
        if(!flipedCards.contains(flipedCard)){
            flipedCards.add(flipedCard)
        }
        //Si la lista ya llego a su maximo
        if(flipedCards.size == 5){
            Toast.makeText(this, getString(R.string.text_game_win),Toast.LENGTH_LONG).show()
            flipAll(R.drawable.icon_cheems)
        }
    }

    fun flipAll(r: Int){
        for(i in 1..6){
            val btnCard= findViewById<View>(
                resources.getIdentifier("card$i", "id", this.packageName)
            ) as ImageButton
            btnCard.setBackgroundResource(r)
        }
    }

    fun flip(card: Int){
        if (card == gameOverCard){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            //Si la version del usuario es mayor a la version 12 hara esto
            val vibratorAmin = applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorAmin.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            //Si es menor entonces hara esto
            val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1000)
        }
            Toast.makeText(this, getString(R.string.text_game_over),Toast.LENGTH_LONG).show()

            for(i in 1..6){
                val btnCard= findViewById<View>(
                    resources.getIdentifier("card$i", "id", this.packageName)
                ) as ImageButton
                if (i == card){
                    btnCard.setBackgroundResource(R.drawable.yo)
                }else{
                    btnCard.setBackgroundResource(R.drawable.icon_cheems)
                }
            }

        }else{
            //continua en el juego
            val btnCard= findViewById<View>(
                resources.getIdentifier("card$card", "id", this.packageName)
            ) as ImageButton
            btnCard.setBackgroundResource(R.drawable.icon_cheems)
            win(btnCard)
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.card1 -> {flip(1)}
            R.id.card2 -> {flip(2)}
            R.id.card3 -> {flip(3)}
            R.id.card4 -> {flip(4)}
            R.id.card5 -> {flip(5)}
            R.id.card6 -> {flip(6)}
            R.id.btn_restart -> {restart()}

        }
    }
}