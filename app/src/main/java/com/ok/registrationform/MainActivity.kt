package com.ok.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        Register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        Login.setOnClickListener{
            if(checking()){
                val email = Email.text.toString()
                val password = Password.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){
                        task->
                        if(task.isSuccessful){
                            var intent = Intent(this, LogIn::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Enter valid UserId and Password", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun checking():Boolean
    {
        if(Email.text.toString().trim{it<=' '}.isNotEmpty()
            && Password.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false
    }
}