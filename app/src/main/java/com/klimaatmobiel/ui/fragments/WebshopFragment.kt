package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentWebshop2Binding

/**
 * A simple [Fragment] subclass.
 */
class WebshopFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWebshop2Binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_webshop2, container, false)
        binding.shoppingcartButton.setOnClickListener {view : View ->
            view.findNavController().navigate(R.id.action_webshopFragment_to_shoppingcartfragment)
        }

        binding.detailsButton1.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_webshopFragment_to_productDetailFragment)
        }

        return binding.root
        }


}
