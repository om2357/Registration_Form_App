package com.ok.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.jar.Attributes

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        Countinue.setOnClickListener{
            if (checking()){
                val name = Name.text.toString()
                val email = EmailRegister.text.toString()
                val password = PasswordRegister.text.toString()
                val phone = Phonee.text.toString()

                val user = hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "email" to email
                )
                val Users = db.collection("USERS")
                val query = Users.whereEqualTo("email",email).get()
                    .addOnSuccessListener {
                        tasks ->
                        if( tasks .isEmpty)
                        {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this){
                                    task->
                                    if (task.isSuccessful)
                                    {
                                        Users.document(email).set(user)
                                        val intent = Intent(this, LogIn::class.java)
                                        intent. putExtra("email", email)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
                                        val intent = Intent(this, RegisterActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                        }
                        else
                        {
                            Toast.makeText(this, "User Already Registered", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun checking():Boolean{
        if(Name.text.toString().trim{it<=' '}.isNotEmpty()
            && Phonee.text.toString().trim{it<=' '}.isNotEmpty()
            && EmailRegister.text.toString().trim{it<=' '}.isNotEmpty()
            && PasswordRegister.text.toString().trim{it<=' '}.isNotEmpty()
        )
        {
            return true
        }
        return false
    }
}

