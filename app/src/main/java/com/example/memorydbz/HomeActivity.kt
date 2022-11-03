package com.example.memorydbz

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    lateinit var mediaPlayer7 : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mediaPlayer7 = MediaPlayer.create(this,R.raw.sound_win)
        mediaPlayer7?.start()

        val btnWin = findViewById<Button>(R.id.btnWin)
        btnWin.setOnClickListener{
            val send4 = Intent(this, MainActivity::class.java)
            startActivity(send4)
            mediaPlayer7.stop()
        }
    }
}