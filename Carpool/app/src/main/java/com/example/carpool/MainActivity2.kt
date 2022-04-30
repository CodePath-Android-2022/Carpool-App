package com.example.carpool

import android.content.Intent
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {
    var videoView: VideoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val buttonClick = findViewById<Button>(R.id.start)
        buttonClick.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
        videoView = findViewById(R.id.videoview)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.bg_video)
        videoView?.setVideoURI(uri)
        videoView?.start()
        videoView?.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
    }

    override fun onResume() {
        videoView?.resume()
        super.onResume()
    }

    override fun onRestart() {
        videoView?.start()
        super.onRestart()
    }

    override fun onPause() {
        videoView?.suspend()
        super.onPause()
    }

    override fun onDestroy() {
        videoView!!.stopPlayback()
        super.onDestroy()
    }
}