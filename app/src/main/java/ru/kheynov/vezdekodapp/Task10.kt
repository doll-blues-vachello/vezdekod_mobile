package ru.kheynov.vezdekodapp

import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kheynov.vezdekodapp.Util.PathUtils
import java.io.*


class Task10 : AppCompatActivity() {
	private lateinit var playerInstance: SimpleExoPlayer
	private lateinit var save_file_button: FloatingActionButton
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_task10)

		val playerView: PlayerView = findViewById(R.id.player_view)
		save_file_button = findViewById(R.id.fab)


		val filePath = Uri.parse(intent.getStringExtra("file_path").toString())!!
		playerInstance = SimpleExoPlayer.Builder(this).build()
		playerView.player = playerInstance
		val mediaItem = MediaItem.fromUri(filePath)
		playerInstance.setMediaItem(mediaItem)
		playerInstance.prepare()
		playerInstance.play()
		save_file_button.setOnClickListener {
			savefile(filePath)
		}
	}

	override fun onDestroy() {
		playerInstance.release()
		super.onDestroy()

	}


	fun savefile(sourceuri: Uri) {
		val sourceFilename: String = PathUtils.getPath(this, sourceuri)/*sourceuri.path.toString()*/
		val destinationFilename =
			this.getExternalFilesDir(null)?.absolutePath + File.separatorChar.toString() + "vezdekod.mp4"

		val text_file_saved = "File saved as $destinationFilename"
		Toast.makeText(this, text_file_saved, Toast.LENGTH_LONG).show()

		var bis: BufferedInputStream? = null
		var bos: BufferedOutputStream? = null
		try {
			bis = BufferedInputStream(FileInputStream(sourceFilename))
			bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
			val buf = ByteArray(1024)
			bis.read(buf)
			do {
				bos.write(buf)
			} while (bis.read(buf) !== -1)
		} catch (e: IOException) {
			Log.e("ERROR", e.stackTraceToString())
		} finally {
			try {
				bis?.close()
				bos?.close()
			} catch (e: IOException) {
				e.printStackTrace()
			}
		}
	}

	private fun getRealPath(uri: Uri): String {
		val docId = DocumentsContract.getDocumentId(uri)
		val split = docId.split(":".toRegex()).toTypedArray()
		val type = split[0]
		if ("primary".equals(type, ignoreCase = true)) {
			return this.getExternalFilesDir(null)?.absolutePath + "/" + split[1]
		}
		return "error"
	}
}