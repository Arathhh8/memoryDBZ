package com.example.memorydbz

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LostActivity : AppCompatActivity() {
    lateinit var mediaPlayer6: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)

        mediaPlayer6 = MediaPlayer.create(this,R.raw.sound_lost)
        mediaPlayer6?.start()

        val btnTryAgain = findViewById<Button>(R.id.btnTryAgain)
        btnTryAgain.setOnClickListener{
            val send4 = Intent(this, MainActivity::class.java)
            startActivity(send4)
            mediaPlayer6.stop()
        }
    }
}