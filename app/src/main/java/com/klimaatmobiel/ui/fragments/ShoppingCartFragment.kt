package com.klimaatmobiel.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentShoppingCartBinding
import com.example.projecten3android.databinding.FragmentWebshopBinding
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import timber.log.Timber


class ShoppingCartFragment : Fragment() {


    private lateinit var viewModel: WebshopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentShoppingCartBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        binding.webshopViewModel = viewModel




        return binding.root


    }


}