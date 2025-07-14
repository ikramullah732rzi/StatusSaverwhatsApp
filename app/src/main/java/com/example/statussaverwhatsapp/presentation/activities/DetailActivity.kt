package com.example.statussaverwhatsapp.presentation.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.statussaverwhatsapp.databinding.ActivityDetailBinding
import com.example.statussaverwhatsapp.utils.IMAGE_URI
import com.example.statussaverwhatsapp.utils.VIDEO_URI

class DetailActivity : AppCompatActivity() {

    private lateinit var mediaController: MediaController
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mediaController = MediaController(this)
        val videoUri = intent.getStringExtra(VIDEO_URI)
        val imageUri = intent.getStringExtra(IMAGE_URI)

        when {
            !videoUri.isNullOrEmpty() -> {
                Log.d("DetailActivity", "Video URI: $videoUri")
                binding.imageView.visibility = View.GONE
                binding.videoView.visibility = View.VISIBLE
                binding.videoView.setVideoURI(Uri.parse(videoUri))
                binding.videoView.setMediaController(mediaController)
                mediaController.setAnchorView(binding.videoView)
                binding.videoView.start()
            }

            !imageUri.isNullOrEmpty() -> {
                Log.d("DetailActivity", "Image URI: $imageUri")
                binding.videoView.visibility = View.GONE
                binding.imageView.visibility = View.VISIBLE
                binding.imageView.setImageURI(Uri.parse(imageUri))
            }

            else -> {
                Log.e("DetailActivity", "No URI provided")
                binding.imageView.visibility = View.GONE
                binding.videoView.visibility = View.GONE
            }
        }
        binding.materialToolbar2.setNavigationOnClickListener {
            finish()
        }
    }


}
