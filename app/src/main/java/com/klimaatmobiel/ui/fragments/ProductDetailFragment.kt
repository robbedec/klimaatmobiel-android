package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentProductDetailBinding
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.ViewModelFactories.ProductDetailViewModelFactory
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel

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
        val viewModelFactory = ProductDetailViewModelFactory(KlimaatmobielRepository(apiService))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel::class.java)

        binding.productDetailViewModel = viewModel

        return binding.root
    }


}
