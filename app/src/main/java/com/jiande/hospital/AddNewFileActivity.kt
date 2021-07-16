package com.jiande.hospital

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_addnewfile.*
import java.util.*

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddNewFileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnewfile)
        val intent = getIntent()
        val user:user_data? = intent.getSerializableExtra("user_data") as user_data?
        Log.d("AddNewFileActivity","${user?.mail}  ${user?.password}")
        select_photo_bottom.setOnClickListener(){
            Log.d("AddNewFileActivity","select Image")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
            //finish()
        }

        upload_bottom.setOnClickListener(){
            Log.d("AddNewFileActivity","Upload Image")
            uploadImageToFireBaseStorage()
        }

        Back_Login.setOnClickListener(){
            Log.d("AddNewFileActivity","AddNewFileActivity Try to Go Login Activity")
            finish()
        }
    }
    private fun uploadImageToFireBaseStorage(){
        if(selectedPhotoUri == null)
        {
            Log.d("AddNewFileActivity","Null , selectedPhotoUri : ${selectedPhotoUri.toString()}")
            return
        }
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        Log.d("AddNewFileActivity","dddddddddd")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("AddNewFileActivity","Successfully : ${it.metadata?.path}")
            }
    }


    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was..
            Log.d("AddNewFileActivity","Photo was selected")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            select_photo_bottom.setBackgroundDrawable(bitmapDrawable)

            Log.d("AddNewFileActivity","${selectedPhotoUri.toString()}")
        }
        else{
            Log.d("AddNewFileActivity","Photo wasnt selected")
        }
    }

    /*private fun saveUserdataToFireBase(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid,,)

        ref.setValue()
    }

    class User(val uid:String, val username:String, val photourl:String)*/

}