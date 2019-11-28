package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentWebshopBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.adapters.ProductListAdapter
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

/**
 * A simple [Fragment] subclass.
 */
class WebshopFragment : Fragment() {


    private lateinit var viewModel: WebshopViewModel
    private lateinit var binding: FragmentWebshopBinding
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentWebshopBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


        binding.webshopViewModel = viewModel

        // Bind clickListener and decide the action based on an Int
        adapter = ProductListAdapter(ProductListAdapter.OnClickListener {
            product, action ->  viewModel.onProductClicked(product, action)
        })

        binding.productsList.layoutManager = createGridLayoutManager()
        binding.productsList.adapter = adapter


        // Filter the recyclerView based on text
        binding.filterText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()){
                    // Resubmit the full list and apply the new filter
//                    adapter.filter.filter(s)
                    viewModel.filterListString(s)
                    adapter.addHeaderAndSubmitList(viewModel.filteredList.value)
                } else {
                    adapter.addHeaderAndSubmitList(viewModel.group.value!!.project.products)
                }
                adapter.notifyDataSetChanged()
            }
        })

        var productList = viewModel.group.value!!.project.products
        val cats = productList.map { prod -> prod.category!!.categoryName }.toSortedSet()
        val catList = listOf("GEEN FILTER") + cats.toList()

        val dropAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, catList)


        binding.positionSpinner.adapter = dropAdapter

        binding.positionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    viewModel.filterListCategoryName("")
                    adapter.addHeaderAndSubmitList(viewModel.filteredList.value)
                } else {
                    viewModel.filterListCategoryName(parent.getItemAtPosition(position).toString())
                    adapter.addHeaderAndSubmitList(viewModel.filteredList.value)
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.status.observe(this, Observer {
            when(it) {
                KlimaatMobielApiStatus.ERROR -> {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.project_code_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.onErrorShown()
                }
            }
        })

        // Populate recyclerView when the data is received
        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it.project.products)
            }
        })
    }

    override fun onPause() {
        super.onPause()

        // Deallocate observers
        viewModel.status.removeObservers(this)
        viewModel.group.removeObservers(this)
    }

    /**
     * Decide when a list item should span 3 widths.
     *
     * itemViewType = 0 -> HEADER
     * itemViewType = 1 -> PRODUCT
     *
     * @return [GridLayoutManager] with the correct span size for each item.
     */
    private fun createGridLayoutManager(): GridLayoutManager {
        val manager = GridLayoutManager(context, 3)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(adapter.getItemViewType(position) == 1) {
                    1
                } else {
                    3
                }
            }

        }
        return manager
    }
}
