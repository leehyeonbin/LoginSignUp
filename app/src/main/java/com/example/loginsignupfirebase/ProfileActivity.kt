package com.example.loginsignupfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.loginsignupfirebase.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var mbinding : ActivityProfileBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        // Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // handle click, logout
        mbinding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        // check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // user not null, user is logged in, get user info
            val email = firebaseUser.email
                // set to text view
            mbinding.emailTv.text = email

        }
        else {
            // user is null, user is not loggedin, goto login activity
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}