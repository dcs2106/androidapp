package com.jiande.hospital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Register_Bottom.setOnClickListener{
            performRegister()
        }

        Register_already.setOnClickListener{
            Log.d("MainActivity","Try to Go Login Activity")
            //launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun performRegister(){
        val username = Register_Username.text.toString()
        val mail = Register_Email.text.toString()
        val password = Register_Password.text.toString()
        Log.d("MainActivity","Username is : $username")
        Log.d("MainActivity","Email is : $mail")
        Log.d("MainActivity","Password :  $password")
        //FireBase Authentication to create a user with email and password
        if(mail.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter text in Email/Password",Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener{
                if (!it.isComplete){
                    Log.d("MainActivity","Created")
                    Toast.makeText(this,"This Mail is already registered",Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }
                if (!it.isSuccessful) {
                    Log.d("MainActivity", "Fail created user")
                    return@addOnCompleteListener
                }
                //else if successful
                Log.d("MainActivity","Successfully created user with uid : ${it.result!!.user!!.uid}")
            }
            .addOnFailureListener{
                Log.d("MainActivity","msg : ${it.message}")
                Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
            }
    }
}