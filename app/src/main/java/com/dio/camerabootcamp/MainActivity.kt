package com.dio.camerabootcamp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {


    companion object {
        private val PERMISSION_CODE_IMAGE_PICK = 1000
        private val IMAGE_PICK_CODE = 1001

        private val PERMISSION_CODE_CAMERA_CAPTURE = 2000

    }


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pick_button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE_IMAGE_PICK)
                }
                else {
                    pickImageFronGalery()
                }
            }
            else {
                pickImageFronGalery()
            }
        }

        open_camera_button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                 == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE_CAMERA_CAPTURE)
                }
                else {
                    openCamera()
                }
            }
            else {
                openCamera()

            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) //Ele não usou!
        when(requestCode) {
            PERMISSION_CODE_IMAGE_PICK -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFronGalery()
                }
                else {
                    Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show()

                }
            }
            PERMISSION_CODE_CAMERA_CAPTURE -> {
                if (grantResults.size >1 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
                else {
                    Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFronGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)
        }
    }


}