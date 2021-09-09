package com.example.loginsignupfirebase.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.loginsignupfirebase.LoginActivity
import com.example.loginsignupfirebase.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment(val firebaseAuth: FirebaseAuth?) :Fragment() {

    private var mbinding : FragmentProfileBinding? = null
    private val viewModel by activityViewModels<MainViewModel>()

    constructor() : this(null){

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentProfileBinding.inflate(inflater, container, false)

        mbinding!!.emailTv.text = arguments?.getString("email")
        observeViewModel()
        mbinding!!.logoutBtn.setOnClickListener {
            firebaseAuth?.signOut()
            requireActivity().finish()
            val intent = Intent(getActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        return mbinding?.root


    }

    private fun observeViewModel(){
        viewModel.userEmail.observe(requireActivity(), Observer {
            mbinding!!.emailTv.text = it
        })
    }

    override fun onDestroyView() {
        mbinding = null
        super.onDestroyView()
    }

}