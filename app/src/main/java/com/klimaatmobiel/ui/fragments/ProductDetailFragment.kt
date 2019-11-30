package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.databinding.FragmentProductDetailBinding
import com.klimaatmobiel.data.database.getDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.MainActivity
import com.klimaatmobiel.ui.ViewModelFactories.ProductDetailViewModelFactory
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel

/**
 * Fragment that displays information about a specific product retrieved from the API.
 *
 * @author Robbe Decorte
 */
class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // Display back button in the actionBar
        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Inject apiService and database into the ViewModel and request it.
        val apiService = KlimaatmobielApi.retrofitService
        val viewModelFactory = ProductDetailViewModelFactory(KlimaatmobielRepository(apiService, getDatabase(context!!.applicationContext)), ProductDetailFragmentArgs.fromBundle(requireArguments()).projectId, ProductDetailFragmentArgs.fromBundle(arguments!!).productId)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel::class.java)

        binding.productDetailViewModel = viewModel

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Deallocate observers
        viewModel.product.removeObservers(this)
    }

    override fun onResume() {
        super.onResume()

        viewModel.product.observe(viewLifecycleOwner, Observer {
            binding.product = it
        })
    }
}
