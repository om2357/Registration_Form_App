 package com.ok.registrationform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_log_in.*

 class LogIn : AppCompatActivity() {
     private  lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return

         val spEmail = sharedPref.getString("Email","1")
        val isLogIn = sharedPref.getString("Email","1")
        logout.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        var Users =

        if(isLogIn == "1"){
            var email = intent.getStringExtra("email")

            if (email!= null){
                setText(email)
                with(sharedPref.edit()){
                    putString("Email",email)
                    apply()

                }
            }
            else{
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            setText(isLogIn)
        }

    }
    private fun setText(email: String?)
    {
        db = FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener { tasks ->
                    name.text = tasks.get("Name").toString()
                    phone.text = tasks.get("Phone").toString()
                    emailLog.text = tasks.get("email").toString()

                }
        }
    }
}