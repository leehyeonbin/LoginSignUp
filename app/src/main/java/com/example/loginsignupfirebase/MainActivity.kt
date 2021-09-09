package com.example.loginsignupfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.loginsignupfirebase.databinding.ActivityMainBinding
import com.example.loginsignupfirebase.fragment.MainViewModel
import com.example.loginsignupfirebase.fragment.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var mbinding : ActivityMainBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var profileFragment : ProfileFragment

    //viewModel 선언
    private val viewModel by viewModels<MainViewModel>()

    val TAG = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        // navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_host_nav) as NavHostFragment




        // navigation controller
        val navContolloer = navHostFragment.navController

        // Tie the bottom navigation view and navigation
        NavigationUI.setupWithNavController(mbinding.myBottomNav, navContolloer)

        // Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"


        //---------- 디버깅 오류가 나는 지점 ----------------
        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        profileFragment = ProfileFragment(firebaseAuth)
        checkUser()

//         handle click, logout
    }

    private fun checkUser() {
        Log.d(TAG, "checkUser")
        // check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // user not null, user is logged in, get user info
            val email = firebaseUser.email
                // set to text view

            viewModel.setUserEmail(email.toString())
            var bundle = bundleOf("email" to email)
            Log.d("로그","유저 : "+ email)

           // bundle.putString("email", email)

            profileFragment.arguments = bundle
        }
        else {
            // user is null, user is not loggedin, goto login activity
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}