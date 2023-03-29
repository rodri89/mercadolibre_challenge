package com.example.mercadolibrechallenge.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercadolibrechallenge.databinding.SearchFragmentBinding
import com.example.mercadolibrechallenge.ui.adapters.ProductAdapter
import com.example.mercadolibrechallenge.ui.interfaces.NavigateInterface
import com.example.mercadolibrechallenge.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding

    private val viewModel: SearchViewModel by viewModel()

    private val searchHandler = Handler(Looper.getMainLooper())

    lateinit var productAdapter: ProductAdapter

    private var listener: NavigateInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponents()
        setListenerRetryButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NavigateInterface
    }

    override fun onDetach() {
        super.onDetach()
        searchHandler.removeCallbacksAndMessages(null)
        listener = null
    }

    override fun onResume() {
        super.onResume()
        fetchProducts(binding.search.query.toString())
    }

    private fun setupComponents() {
        setupSearchBar()
        setupRecyclerView()
    }

    private fun setupSearchBar() {
        binding.search.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(query: String?): Boolean {
                    searchHandler.removeCallbacksAndMessages(null)
                    searchHandler.postDelayed(
                        {
                            fetchProducts(query?.trim().orEmpty())
                        },
                        SEARCH_TYPE_INTERVAL
                    )
                    return true
                }
            })
            requestFocus()
        }
    }

    private val onClickListener =  ProductAdapter.OnClickListener { product ->
        listener?.goToNextScreen(
            SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(
                product.id
            )
        ) }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(onClickListener)

        with(binding.recyclerViewProducts) {
            layoutManager = LinearLayoutManager(requireContext(), null, LinearLayoutManager.VERTICAL, 0)
            addItemDecoration(DividerItemDecoration(
                this.context,
                LinearLayoutManager.VERTICAL
            ))
            setHasFixedSize(true)
            adapter = productAdapter
        }

        productAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.btnRetry.isVisible = false
                binding.shimmer.shimmerLayout.isVisible = true
            }
            else {
                binding.shimmer.shimmerLayout.isVisible = false

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        binding.btnRetry.isVisible = true
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun fetchProducts(query: String) {
        lifecycleScope.launch {
            viewModel.fetchProduct(query).distinctUntilChanged().collectLatest {
                productAdapter.submitData(it)
            }
        }
    }

    private fun setListenerRetryButton() {
        binding.btnRetry.setOnClickListener {
            productAdapter.retry()
        }
    }

    private companion object {
        const val SEARCH_TYPE_INTERVAL = 600L
    }
}