package com.example.loginsignupfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.loginsignupfirebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    // VieBinding
    private lateinit var mbinding : ActivitySignUpBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog : ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password =""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mbinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        // Configure Actionbar, enable back button
        actionBar = supportActionBar!!
        actionBar.title="Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please...wait...")
        progressDialog.setMessage("Creating account in...")
        progressDialog.setCanceledOnTouchOutside(false)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // handle click, begin sigup
        mbinding.signUpBtn.setOnClickListener {
            // validate data
            validateData()
        }
    }

    private fun validateData() {
        // get data
        email = mbinding.emailEt.text.toString().trim()
        password = mbinding.passwordEt.text.toString().trim()

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
            mbinding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)) {
            // password isn't entered
            mbinding.passwordEt.error = "Please enter password"
        }
        else if (password.length<6) {
            // password length is less than
            mbinding.passwordEt.error = "Password must atleast 6 chracters long"
        }
        else {
            // data is valid, continue signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        // show progress
        progressDialog.show()

        // create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // signup success
                progressDialog.dismiss()
                // get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email ${email}",Toast.LENGTH_SHORT).show()

                // open profile
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }.addOnFailureListener { e->
                // signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}