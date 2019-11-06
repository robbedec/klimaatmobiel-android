package com.klimaatmobiel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projecten3android.databinding.GridListItemBinding
import com.klimaatmobiel.domain.OrderItem
import com.klimaatmobiel.domain.Product
import kotlinx.android.synthetic.main.grid_list_item.view.*

class ProductListAdapter(val onClickListener: OnClickListener) : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(DiffCallback), Filterable {

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filtered: MutableList<Product> = ArrayList()
                var result: FilterResults = FilterResults()


                for (x in 0 until itemCount) {
                    if (getItem(x).productName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filtered.add(getItem(x))
                    }
                }

                result.values = filtered
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results!!.values as List<Product>)
                notifyDataSetChanged()
            }

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(GridListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.add_to_cart_image.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }

    class ProductViewHolder(private var binding: GridListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }
}