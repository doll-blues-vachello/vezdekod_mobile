package ru.kheynov.vezdekodapp

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import android.app.Activity




class MainActivity : AppCompatActivity() {
	private val REQUEST_EXTERNAL_STORAGE = 1
	private val PERMISSIONS_STORAGE = arrayOf<String>(
		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.WRITE_EXTERNAL_STORAGE
	)
	val videoUri = "file:///storage/emulated/0/videos/rickroll.mp4"
	private lateinit var playerInstance: SimpleExoPlayer

	fun verifyStoragePermissions(activity: Activity?) {
		// Check if we have write permission
		val permission = ActivityCompat.checkSelfPermission(activity!!,
			Manifest.permission.WRITE_EXTERNAL_STORAGE)
		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
				activity,
				PERMISSIONS_STORAGE,
				REQUEST_EXTERNAL_STORAGE
			)
		}
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		verifyStoragePermissions(this)
		val playerView: PlayerView = findViewById(R.id.player_view)

		playerInstance = SimpleExoPlayer.Builder(this).build()
		playerView.player = playerInstance
		val mediaItem = MediaItem.fromUri(Uri.parse(videoUri))
		playerInstance.setMediaItem(mediaItem)
		playerInstance.prepare()
		playerInstance.play()
	}

	override fun onDestroy() {
		playerInstance.release()
		super.onDestroy()

	}
}