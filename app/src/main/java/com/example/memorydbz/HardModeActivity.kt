package com.example.memorydbz

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class HardModeActivity : AppCompatActivity() {
    private lateinit var cards: List<MemoryCard>
    private lateinit var buttons: List<ImageButton>
    private var indexOfSingleSelectedCard: Int? = null
    lateinit var mediaPlayer3: MediaPlayer
    lateinit var mediaPlayer4 : MediaPlayer
    var tvResult = 0
    var tvTrysResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_mode)

        val bundle = intent.extras
        val dato = bundle?.getString("Data")
        val etDatoRecibido = findViewById<TextView>(R.id.etDatoRecibido)
        etDatoRecibido.text = "Player: " + dato.toString()
        val freeza1 = findViewById<ImageButton>(R.id.freeza1)
        val freeza2 = findViewById<ImageButton>(R.id.freeza2)
        val goku1 = findViewById<ImageButton>(R.id.goku1)
        val goku2 = findViewById<ImageButton>(R.id.goku2)
        val gohan1 = findViewById<ImageButton>(R.id.gohan1)
        val gohan2 = findViewById<ImageButton>(R.id.gohan2)
        val jiren1 = findViewById<ImageButton>(R.id.jiren1)
        val jiren2 = findViewById<ImageButton>(R.id.jiren2)
        val piccolo1 = findViewById<ImageButton>(R.id.piccolo1)
        val piccolo2 = findViewById<ImageButton>(R.id.piccolo2)
        val majinboo1 = findViewById<ImageButton>(R.id.majinboo1)
        val majinboo2 = findViewById<ImageButton>(R.id.majinboo2)
        val vegeta1 = findViewById<ImageButton>(R.id.vegeta1)
        val vegeta2 = findViewById<ImageButton>(R.id.vegeta2)
        val numero171 = findViewById<ImageButton>(R.id.numero171)
        val numero172 = findViewById<ImageButton>(R.id.numero172)


        mediaPlayer3 = MediaPlayer.create(this,R.raw.sound_battle2)
        mediaPlayer4 = MediaPlayer.create(this,R.raw.sound_matched)
        mediaPlayer3?.start()

        val images = mutableListOf(R.drawable.goku,R.drawable.gohan,R.drawable.piccolo,
            R.drawable.vegeta,R.drawable.freza,R.drawable.jiren,R.drawable.majinboo,
            R.drawable.numero17)

        images.addAll(images)
        //Random images
        images.shuffle()

        buttons = listOf(freeza1, freeza2, goku1, goku2, gohan1,gohan2, jiren1, jiren2,
            piccolo1,piccolo2,majinboo1,majinboo2,vegeta1,vegeta2,numero171,numero172)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "click button")
                updateModels(index)
                updateViews()
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.dragon_ball3)
        }
    }

    private fun updateModels(position: Int) {

        val card = cards[position]
        //checking error
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        if (indexOfSingleSelectedCard == null) {
            mediaPlayer3.start()
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        var tvTrys = findViewById<TextView>(R.id.tvTrys)
        tvTrysResult += 1
        tvTrys.text = tvTrysResult.toString()
        if(tvTrysResult > 19){
            val send3 = Intent(this, LostActivity::class.java)
            startActivity(send3)
            mediaPlayer3.stop()
        }
        if (cards[position1].identifier == cards[position2].identifier) {
            tvResult += 1
            Toast.makeText(this, "Excellent", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            mediaPlayer4.start()
            mediaPlayer3.pause()
            if (tvResult == 8) {
                Toast.makeText(this, "CONGRATULATIONS", Toast.LENGTH_LONG).show()
                val send6 = Intent(this, HomeActivity::class.java)
                startActivity(send6)
                mediaPlayer3.stop()
            }
        }
    }
}