package com.example.memorydbz

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class GameActivity : AppCompatActivity() {
    private lateinit var cards: List<MemoryCard>
    private lateinit var buttons: List<ImageButton>
    private var indexOfSingleSelectedCard: Int? = null
    lateinit var mediaPlayer5: MediaPlayer
    lateinit var mediaPlayer4 : MediaPlayer
    var tvResult = 0
    var tvTrysResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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


        mediaPlayer5 = MediaPlayer.create(this,R.raw.sound_battle)
        mediaPlayer4 = MediaPlayer.create(this,R.raw.sound_matched)
        mediaPlayer5?.start()

        val images = mutableListOf(R.drawable.goku,R.drawable.gohan,R.drawable.piccolo,
            R.drawable.vegeta,R.drawable.freza,R.drawable.jiren,R.drawable.majinboo,
            R.drawable.numero17)
        // Add each image twice so we can create pairs
        images.addAll(images)
        // Randomize the order of images
        images.shuffle()

        buttons = listOf(freeza1, freeza2, goku1, goku2, gohan1,gohan2, jiren1, jiren2,
            piccolo1,piccolo2,majinboo1,majinboo2,vegeta1,vegeta2,numero171,numero172)


        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!!")
                // Update models
                updateModels(index)
                // Update the UI for the game
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
        // Error checking:
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            mediaPlayer5.start()
            restoreCards()


            indexOfSingleSelectedCard = position
        } else {

            // exactly 1 card was selected previously
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

        tvTrysResult += 1
        var tvTrys = findViewById<TextView>(R.id.tvTrys)
        tvTrys.text = tvTrysResult.toString()

        if (cards[position1].identifier == cards[position2].identifier) {

            var tvPairFound = findViewById<TextView>(R.id.tvPairFound)
            tvResult += 1
            tvPairFound.text = tvResult.toString()
            if(tvResult < 8){
                Toast.makeText(this, "Excellent", Toast.LENGTH_SHORT).show()
            }
            else{
                    Toast.makeText(this, "CONGRATULATIONS", Toast.LENGTH_LONG).show()
            }
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            mediaPlayer4.start()
            mediaPlayer5.pause()

            if (tvResult == 8) {
                val send6 = Intent(this, HomeActivity::class.java)
                startActivity(send6)
                mediaPlayer4.stop()
            }

        }


    }
}