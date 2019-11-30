package com.klimaatmobiel.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.databinding.FragmentShoppingCartBinding
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

class ShoppingCartFragment : Fragment() {

    private lateinit var viewModel: WebshopViewModel
    private lateinit var binding: FragmentShoppingCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentShoppingCartBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        binding.webshopViewModel = viewModel

        // Bind the clickListener for shopping cart actions
        binding.orderPreviewList.adapter = OrderPreviewListAdapter(OrderPreviewListAdapter.OnClickListener{ // add
            viewModel.changeOrderItemAmount(it, true)
        }, OrderPreviewListAdapter.OnClickListener{// minus
            viewModel.changeOrderItemAmount(it, false)
        }, OrderPreviewListAdapter.OnClickListener{// delete
            viewModel.removeOrderItem(it)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.group.observe(this, Observer{
            if(viewModel.posToRefreshInOrderPreviewListItem != -1){
                binding.orderPreviewList.adapter?.notifyItemChanged(viewModel.posToRefreshInOrderPreviewListItem)
            }
        })
    }

    override fun onPause() {
        super.onPause()

        // Deallocate observers
        viewModel.group.removeObservers(this)
    }
}