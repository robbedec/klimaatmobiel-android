package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentWebshopBinding
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.ui.ViewModelFactories.WebshopViewModelFactory
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.adapters.ProductListAdapter
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class WebshopFragment : Fragment() {


    private lateinit var viewModel: WebshopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = FragmentWebshopBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        binding.webshopViewModel = viewModel

        binding.orderPreviewList.adapter = OrderPreviewListAdapter(OrderPreviewListAdapter.OnClickListener{ // add
            viewModel.changeOrderItemAmount(it, true)
        }, OrderPreviewListAdapter.OnClickListener{// minus
            viewModel.changeOrderItemAmount(it, false)
        })



        binding.productsList.adapter = ProductListAdapter(ProductListAdapter.OnClickListener {
            viewModel.addProductToOrder(it)
        })


        return binding.root

    }
}
