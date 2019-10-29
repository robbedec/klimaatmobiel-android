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
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class WebshopFragment : Fragment() {

    private lateinit var binding: FragmentWebshopBinding
    private lateinit var viewModel: WebshopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate xml
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_webshop, container, false)

        // Request the ViewModal
        viewModel = ViewModelProviders.of(this).get(WebshopViewModel::class.java)
        binding.webshopViewModel = viewModel

        binding.lifecycleOwner = this

        var args = WebshopFragmentArgs.fromBundle(arguments!!)
        Timber.i("The projectcode from args: %s", args.code.toString())


        return binding.root
    }
}
