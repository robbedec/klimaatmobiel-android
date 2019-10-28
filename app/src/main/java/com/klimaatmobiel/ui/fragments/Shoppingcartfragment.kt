package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentShoppingcart2Binding

/**
 * A simple [Fragment] subclass.
 */
class Shoppingcartfragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentShoppingcart2Binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoppingcart2, container, false)
        binding.imageButton4.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_shoppingcartfragment_to_webshopFragment)
        }
        return binding.root
    }


}
