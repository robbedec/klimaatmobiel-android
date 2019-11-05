package com.klimaatmobiel.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.ui.adapters.ProductListAdapter




@BindingAdapter("listDataProducts")
fun listDataProducts(recyclerView: RecyclerView, data: List<Product>) {
    val adapter = recyclerView.adapter as ProductListAdapter
    adapter.submitList(data)
}

@BindingAdapter("productNameBinding")
fun productNameBinding(txtView: TextView, product: Product) {

 txtView.text = product.productName + " - €" + product.price.toString()


}