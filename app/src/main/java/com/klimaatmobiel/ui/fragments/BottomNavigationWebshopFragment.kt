package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.projecten3android.R
import com.klimaatmobiel.ui.viewModelFactories.WebshopViewModelFactory
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import com.example.projecten3android.databinding.FragmentBottomNavigationWebshopBinding
import com.klimaatmobiel.data.database.getDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.MainActivity


/**
 * A simple [Fragment] subclass.
 */
class BottomNavigationWebshopFragment : Fragment() {

    private lateinit var viewModel: WebshopViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = FragmentBottomNavigationWebshopBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val group = BottomNavigationWebshopFragmentArgs.fromBundle(arguments!!).group

        // Display groupName in the toolbar and show back button
        (activity as MainActivity).setToolbarTitle("Klimaatmobiel" + " - " + group.groupName + " - " + group.project.projectName)
        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val apiService = KlimaatmobielApi.retrofitService

        // Create and bind the correct viewModel
        viewModel = activity?.run {
            ViewModelProviders.of(this, WebshopViewModelFactory(group, KlimaatmobielRepository(apiService, getDatabase(context!!.applicationContext))))[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.viewModel = viewModel

        // Trigger to display the webshopFragment in the placeholder from the bottomNavigation
        binding.bottomNavigationWebshop.setOnNavigationItemSelectedListener {
            (activity as MainActivity).triggerWebshopBottomNavigation(it)
            true
        }

        // Don't trigger the webShopFragment if the bottomNavigation has a saved state
        // Use the last selected item instead
        if(savedInstanceState == null){
            binding.bottomNavigationWebshop.selectedItemId = R.id.nav_webshop
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Navigate to the product detail fragment
        viewModel.navigateToWebshop.observe(this, Observer {
            if(it != null) {
                findNavController().navigate(
                    BottomNavigationWebshopFragmentDirections.actionBottomNavigationWebshopFragmentToProductDetailFragment2(
                        viewModel.navigateToWebshop.value!![0], // ProjectId
                        viewModel.navigateToWebshop.value!![1]  // ProductId
                    )
                )
                viewModel.onDetailNavigated()
            }
        })
    }

    override fun onPause() {
        super.onPause()

        // Deallocate observers when the fragment is not displayed on screen
        viewModel.navigateToWebshop.removeObservers(this)
    }
}
