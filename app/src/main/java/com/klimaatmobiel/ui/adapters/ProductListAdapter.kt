package com.klimaatmobiel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projecten3android.databinding.GridListItemBinding
import com.example.projecten3android.databinding.ProductListItemBinding
import com.klimaatmobiel.domain.OrderItem
import com.klimaatmobiel.domain.Product
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductListAdapter( val onClickListener: OnClickListener) : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(DiffCallback) {


    class ProductViewHolder(private var binding: GridListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(GridListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.add_to_order.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }


    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }

}