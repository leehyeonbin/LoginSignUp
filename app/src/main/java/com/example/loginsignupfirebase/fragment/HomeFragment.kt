package com.example.loginsignupfirebase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loginsignupfirebase.databinding.FragmentHomeBinding

class HomeFragment :Fragment() {

    private var mbinding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentHomeBinding.inflate(inflater, container, false)


        return mbinding?.root


    }

    override fun onDestroyView() {
        mbinding = null
        super.onDestroyView()
    }

}