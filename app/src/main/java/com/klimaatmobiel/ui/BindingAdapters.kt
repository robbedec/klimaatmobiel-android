package com.klimaatmobiel.ui

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klimaatmobiel.domain.Order
import com.klimaatmobiel.domain.OrderItem
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.adapters.ProductListAdapter




@BindingAdapter("listDataProducts")
fun listDataProductsBinding(recyclerView: RecyclerView, data: List<Product>) {
    val adapter = recyclerView.adapter as ProductListAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDataOrderPreview")
fun listDataOrderPreviewBinding(recyclerView: RecyclerView, data: List<OrderItem>?) {
    val adapter = recyclerView.adapter as OrderPreviewListAdapter
    adapter.submitList(if (data != null) ArrayList(data!!) else null)
}




@BindingAdapter("projectBudgetBinding")
fun projectBudgetBinding(txtView: TextView,  budget: Double) {
    txtView.text = "Budget: €" + budget.toString()
}

@BindingAdapter("groupSpentBinding")
fun groupSpentBinding(txtView: TextView,  totalOrderPrice: Double) {
    txtView.text = "Winkelmandje: €" + totalOrderPrice.toString()
}

@BindingAdapter("productNameBinding")
fun productNameBinding(txtView: TextView, name: String) {
 txtView.text = name
}


@BindingAdapter("productPriceBinding")
fun productPriceBinding(txtView: TextView, price: Double) {
    txtView.text = "Prijs: €" + price.toString()
}


@BindingAdapter("orderItemAmountBinding")
fun orderItemAmountBinding(txtView: TextView, amount: Int) {
    txtView.text = amount.toString()
}

@BindingAdapter("btnCheckoutOrderBinding")
fun btnCheckoutOrderBinding(btn: Button, totalPrice: Double) {
    btn.text = "Voltooi bestelling van €" + totalPrice.toString()
}

@BindingAdapter("orderItemTotalPriceBinding")
fun orderItemTotalPriceBinding(txtView: TextView, oi: OrderItem) {
    txtView.text = "€" + (oi.amount * oi.product!!.price).toString()
}