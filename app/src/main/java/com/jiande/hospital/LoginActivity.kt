package com.jiande.hospital

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Login_Button.setOnClickListener{
            //val username = Register_Username.text.toString()
            val mail = Login_Email.text.toString()
            val password = Login_Password.text.toString()
            //Log.d("MainActivity","Username is : $username")
            Log.d("MainActivity","Email is : $mail")
            Log.d("MainActivity","Password :  $password")
            //FireBase Authentication to create a user with email and password
            if(mail.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please enter text in Email/Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) {
                        Log.d("LoginActivity", "Fail created user")
                        Toast.makeText(this,"msg : login fail",Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    //else if successful
                    Log.d("LoginActivity","Successfully signedin user with uid : ${it.result!!.user!!.uid}")
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AddNewFileActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Log.d("LoginActivity","msg : ${it.message}")
                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener
                }

        }

        Login_BackReg.setOnClickListener(){
            Log.d("MainActivity","LoginActivity Try to Go Main Activity")
            finish()
        }
    }
}