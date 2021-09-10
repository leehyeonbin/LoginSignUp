package com.example.loginsignupfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.loginsignupfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var mbinding : ActivityLoginBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        // configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        // configure dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please...wait...")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        // init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        cheakUser()

        // handle click, open SignUpActivity
        mbinding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        // handle click, begin login
        mbinding.loginBtn.setOnClickListener {
            // before logging in, validate data
            validataDate()
        }
    }

    private fun validataDate() {
        // get data
        email = mbinding.emailEt.text.toString().trim()
        password = mbinding.passwordEt.text.toString().trim()

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
            mbinding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            // no password entered
            mbinding.passwordEt.error = "Please enter password"
        }
        else {
            // data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        // show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
            //login success
                progressDialog.dismiss()
                // get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                // open profile
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        }
            .addOnFailureListener { e->
            //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Logged in at &$email",Toast.LENGTH_SHORT).show()
            }
    }

    private fun cheakUser() {
        // if user is already logged in go to profile activity
        // get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // user is already logged in
                Log.d("EMAIL", firebaseUser.email.toString())
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}