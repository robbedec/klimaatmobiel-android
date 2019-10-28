package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentMainMenuBinding
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModel: MainMenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate xml
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)

        // Request the ViewModal
        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel::class.java)
        binding.mainMenuViewModel = viewModel

        binding.lifecycleOwner = this

        binding.webshopButton.setOnClickListener {
            viewModel.navigateToWebshop.value = true
        }

        viewModel.navigateToWebshop.observe(this, Observer {
            findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragment2ToWebshopFragment())
            viewModel.onWebshopNavigated()
        })

        return binding.root
    }
}



