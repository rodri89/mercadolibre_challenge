package com.example.mercadolibrechallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mercadolibrechallenge.databinding.ErrorFragmentBinding

class ErrorFragment: Fragment() {

    private lateinit var binding: ErrorFragmentBinding

    private val args: ErrorFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ErrorFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextComponent()
        setupButton()
    }

    private fun setupTextComponent(){
        binding.message.text = args.errorMessage
    }

    private fun setupButton() {
        binding.materialButton.setOnClickListener {
            onDestroy()
        }
    }

}