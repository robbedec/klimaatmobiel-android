package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentProductDetailBinding
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.ViewModelFactories.ProductDetailViewModelFactory
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel
import timber.log.Timber

/**
 * Fragment that displays information about a specific product retrieved from the API.
 *
 * @author Robbe Decorte
 */
class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // Inject apiService into the ViewModel and request it.
        val apiService = KlimaatmobielApi.retrofitService
        val viewModelFactory = ProductDetailViewModelFactory(KlimaatmobielRepository(apiService), ProductDetailFragmentArgs.fromBundle(requireArguments()).projectId, ProductDetailFragmentArgs.fromBundle(arguments!!).productId)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel::class.java)

        binding.productDetailViewModel = viewModel

        viewModel.product.observe(viewLifecycleOwner, Observer {
            binding.product = it
        })

        binding.returnImage.setOnClickListener {
            activity!!.onBackPressed()
        }

        return binding.root
    }
}
