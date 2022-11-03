package com.example.memorydbz

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer1 : MediaPlayer
    lateinit var mediaPlayer5 : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer1 = MediaPlayer.create(this,R.raw.sond_main)
        mediaPlayer1?.start()


    }

    fun GameActivity(view: View) {
        val etUserName = findViewById<EditText>(R.id.etUserName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val checkCHard = findViewById<CheckBox>(R.id.checkHard)
        val checkEasy = findViewById<CheckBox>(R.id.checkEasy)
        var mediaPlayer2 : MediaPlayer? = MediaPlayer.create(this,R.raw.sound_teletransport)

        if(etUserName.text.isNotEmpty() && etEmail.text.isNotEmpty() && checkEasy.isChecked && !checkCHard.isChecked) {
            val send = Intent(this, GameActivity::class.java)
            send.putExtra("Data", etUserName.text.toString())
            startActivity(send)
            mediaPlayer2?.start()
            mediaPlayer1.stop()
        }
        if(etUserName.text.isNotEmpty() && etEmail.text.isNotEmpty() && checkCHard.isChecked && !checkEasy.isChecked){
            val send2 = Intent(this, HardModeActivity::class.java)
            mediaPlayer5 = MediaPlayer.create(this,R.raw.sound_battle)
            send2.putExtra("Data", etUserName.text.toString())
            startActivity(send2)
            mediaPlayer2?.start()
            mediaPlayer1.stop()
        }
        if(etUserName.text.isNotEmpty() && etEmail.text.isNotEmpty() && checkCHard.isChecked && checkEasy.isChecked){
            Toast.makeText(this,"Select only one mode please",Toast.LENGTH_SHORT).show()
        }
    }

}