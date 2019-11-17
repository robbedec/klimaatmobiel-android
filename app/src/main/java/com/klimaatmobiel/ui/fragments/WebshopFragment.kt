package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentWebshopBinding
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
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
    //private lateinit var binding: WebshopFragmentBinding

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
        }, OrderPreviewListAdapter.OnClickListener{// delete
            viewModel.removeOrderItem(it)
        })


        val adapter = ProductListAdapter(ProductListAdapter.OnClickListener {
            product, action ->  viewModel.onProductClicked(product, action)
        })


        viewModel.group.observe(this, Observer{
            if(viewModel.posToRefreshInOrderPreviewListItem != -1){
                binding.orderPreviewList.adapter?.notifyItemChanged(viewModel.posToRefreshInOrderPreviewListItem)
            }
        })

        /**
         * Decide when a list item should span 2 widths
         *
         * itemViewType = 0 -> HEADER
         * itemViewType = 1 -> PRODUCT
         */
        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(adapter.getItemViewType(position) == 1) {
                    1
                } else {
                    2
                }
            }

        }

        binding.productsList.layoutManager = manager
        binding.productsList.adapter = adapter

        /**
         *  Populate the [RecyclerView] when the data is received from the back-end
         */
        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it.project.products)
            }
        })

        /**
         * Filter the product list
         * Activates for every key-stroke
         */
        binding.filterText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timber.i("1")

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Timber.i("2")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timber.i("removed")
                if(!s.isNullOrEmpty()){
                    // Resubmit the full list and apply the new filter
                    adapter.filter.filter(s)
                } else {
                    adapter.addHeaderAndSubmitList(viewModel.group.value!!.project.products)
                }
                adapter.notifyDataSetChanged()
            }
        })



        return binding.root
    }
}
