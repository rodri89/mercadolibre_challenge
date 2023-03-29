package com.example.mercadolibrechallenge.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mercadolibrechallenge.databinding.WelcomeFragmentBinding
import com.example.mercadolibrechallenge.ui.interfaces.NavigateInterface

class WelcomeFragment: Fragment() {

    private lateinit var binding: WelcomeFragmentBinding

    private var listener: NavigateInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.materialButtonContinuar.setOnClickListener {
            listener?.goToNextScreen(
                WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment()
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NavigateInterface
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}