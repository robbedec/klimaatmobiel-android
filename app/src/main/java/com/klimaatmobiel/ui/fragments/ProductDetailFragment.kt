package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentProductDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProductDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product_detail, container, false)
        binding.imageButton4.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_productDetailFragment_to_webshopFragment)
        }
        return binding.root
    }


}
