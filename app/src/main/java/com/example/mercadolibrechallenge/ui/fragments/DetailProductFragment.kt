package com.example.mercadolibrechallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mercadolibrechallenge.R
import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.data.actions.ProductAction
import com.example.mercadolibrechallenge.databinding.DetailProductFragmentBinding
import com.example.mercadolibrechallenge.ui.activity.SearchActivity
import com.example.mercadolibrechallenge.ui.insertImage
import com.example.mercadolibrechallenge.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProductFragment : Fragment() {

    private lateinit var binding: DetailProductFragmentBinding

    private val viewModel: SearchViewModel by viewModel()

    private val args: DetailProductFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailProductFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            product.observe(viewLifecycleOwner, ::handleAction)
            getProductId(args.productId)
        }
    }

    private fun handleAction(productAction: ProductAction) {
        when(productAction) {
            is ProductAction.ProductSuccess -> updateUi(productAction.product)
            is ProductAction.ProductError -> { navigateToError(DETAIL_PRODUCT_ERROR) }
            is ProductAction.ProductNetworkError -> { navigateToError(DETAIL_PRODUCT_ERROR_NETWORK) }
        }
    }

    private fun updateUi(product: ProductResponse) {
        with(binding) {
            title.text = product.title
            image.insertImage(product.thumbnail)
            price.text = getString(R.string.price, product.price)
            description.text = getString(R.string.features)

            product.attributes.filter { it.hasValidAttribute() }.forEach {
                val textView = TextView(requireContext())
                textView.text =getString(R.string.description_value, it.name, it.valueName)
                binding.descriptionList.addView(textView)
            }
            shimmer.shimmerDetailProduct.isVisible = false
        }
    }

    private fun navigateToError(errorMessage: String) {
        (activity as SearchActivity).goToNextScreen(
            DetailProductFragmentDirections.actionDetailProductFragmentToErrorFragment(
                errorMessage = errorMessage
            )
        )
    }

    private companion object {
        const val DETAIL_PRODUCT_ERROR = "Error"
        const val DETAIL_PRODUCT_ERROR_NETWORK = "Error Network"
    }

}