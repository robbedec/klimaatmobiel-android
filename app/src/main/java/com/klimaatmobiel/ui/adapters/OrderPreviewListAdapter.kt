package com.klimaatmobiel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projecten3android.databinding.OrderPreviewListItemBinding
import com.klimaatmobiel.domain.OrderItem
import kotlinx.android.synthetic.main.order_preview_list_item.view.*

class OrderPreviewListAdapter(val onClickListenerAdd: OnClickListener, val onClickListenerMinus: OnClickListener, val onClickListenerRemove: OnClickListener) : ListAdapter<OrderItem, OrderPreviewListAdapter.OrderItemViewHolder>(DiffCallback) {


    class OrderItemViewHolder(private var binding: OrderPreviewListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderItem: OrderItem) {
            binding.orderItem = orderItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderItem>() {
        override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {

            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(OrderPreviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val orderItem = getItem(position)
        holder.itemView.btn_plus.setOnClickListener {
            onClickListenerAdd.onClick(orderItem)
        }
        holder.itemView.btn_minus.setOnClickListener {
            onClickListenerMinus.onClick(orderItem)
        }
        holder.itemView.cart_remove_order_item.setOnClickListener {
            onClickListenerRemove.onClick(orderItem)
        }
        holder.bind(orderItem)
    }


    class OnClickListener(val clickListener: (orderItem: OrderItem) -> Unit) {
        fun onClick(orderItem: OrderItem) = clickListener(orderItem)
    }

}