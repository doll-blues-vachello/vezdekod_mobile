package ru.kheynov.vezdekodapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {
	private val REQUEST_EXTERNAL_STORAGE = 1
	private val PERMISSIONS_STORAGE = arrayOf(
		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.WRITE_EXTERNAL_STORAGE
	)
	private var filePath = ""

	private lateinit var button_task10: Button
	private lateinit var button_task20: Button
	private lateinit var button_task30: Button
	private lateinit var button_choose_file: Button
	private lateinit var tv_file_info: TextView

	private fun verifyStoragePermissions(activity: Activity?) {
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

		button_task10 = findViewById(R.id.button_task10)
		button_task20 = findViewById(R.id.button_task20)
		button_task30 = findViewById(R.id.button_task30)
		button_choose_file = findViewById(R.id.button_choose_file)
		tv_file_info = findViewById(R.id.tv_file_info)

		button_choose_file.setOnClickListener {
			val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
			photoPickerIntent.type = "video/*"
			startActivityForResult(photoPickerIntent, 1)
		}

		button_task10.setOnClickListener {
			if (!checkFilePathIsEmpty(filePath)) {
				val intent = Intent(this, Task10::class.java)
				intent.putExtra("file_path", filePath)
				startActivity(intent)
			} else {
				Toast.makeText(this, "Please choose a file first", Toast.LENGTH_SHORT).show()
			}
		}
		button_task20.setOnClickListener {
			if (!checkFilePathIsEmpty(filePath)) {
				val intent = Intent(this, Task10::class.java)
				intent.putExtra("file_path", filePath)
				startActivity(intent)
			} else {
				Toast.makeText(this, "Please choose a file first", Toast.LENGTH_SHORT).show()
			}
		}
		button_task30.setOnClickListener {
			if (!checkFilePathIsEmpty(filePath)) {
				val intent = Intent(this, Task10::class.java)
				intent.putExtra("file_path", filePath)
				startActivity(intent)
			} else {
				Toast.makeText(this, "Please choose a file first", Toast.LENGTH_SHORT).show()
			}
		}

	}

	@SuppressLint("SetTextI18n")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode) {
			1 -> {
				if (resultCode == RESULT_OK) {
					val chosenImageUri = data?.data
					Log.i("FILE_RECEIVED_URI", chosenImageUri.toString())
					filePath = chosenImageUri?.toString() ?: ""
					tv_file_info.text = "Chosen file: $filePath"
				}
			}
		}
	}

	private fun checkFilePathIsEmpty(filePath: String) = filePath.isEmpty()
}