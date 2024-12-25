package com.hieunt.fileexplorer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val rootFragment = RootFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission denied => request permission")
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1234)
            } else
                Log.v("TAG", "Permission granted")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.ListFile, rootFragment)
            .commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newFolder -> {

                true
            }
            R.id.newText -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1234) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission granted")
            } else {
                Log.v("TAG", "Permission denied")
            }
        }
    }

}