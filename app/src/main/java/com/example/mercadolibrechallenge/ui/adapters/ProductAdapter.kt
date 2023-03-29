package com.example.mercadolibrechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadolibrechallenge.R
import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.databinding.ItemDetailBinding
import com.example.mercadolibrechallenge.ui.insertImage

class ProductAdapter(private val click: OnClickListener) : PagingDataAdapter<ProductResponse, ProductViewHolder>(PRODUCT_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile, click)
        }
    }

    companion object {
        private val PRODUCT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductResponse>() {
            override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean =
                oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (productResponse: ProductResponse) -> Unit) {
        fun onClick(productResponse: ProductResponse) = clickListener(productResponse)
    }
}

class ProductViewHolder(
    private val binding: ItemDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductResponse, click: ProductAdapter.OnClickListener) {
        binding.apply {
            image.insertImage(product.thumbnail)
            title.text = product.title
            price.text = root.resources.getString(R.string.price, product.price)
            root.setOnClickListener { click.onClick(product) }
        }
    }
}

