package com.example.mercadolibrechallenge.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.mercadolibrechallenge.R
import com.example.mercadolibrechallenge.ui.interfaces.NavigateInterface

class SearchActivity : AppCompatActivity(), NavigateInterface {

    private fun currentNavController(): NavController = findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
    }

    override fun goToNextScreen(directions: NavDirections) {
        currentNavController().navigate(directions)
    }
}