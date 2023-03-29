package com.example.mercadolibrechallenge.ui

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.insertImage(url: String){
    Glide
        .with(this)
        .load(url)
        .centerCrop()
        .into(this);
}