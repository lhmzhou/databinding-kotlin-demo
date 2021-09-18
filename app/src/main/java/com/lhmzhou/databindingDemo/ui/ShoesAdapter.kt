package com.lhmzhou.databindingDemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lhmzhou.databindingDemo.R
import com.lhmzhou.databindingDemo.data.ShoeEntry
import com.lhmzhou.databindingDemo.databinding.ItemShoeBinding

class ShoeAdapter(private val mListener: ShoeClickListener) :
    RecyclerView.Adapter<ShoeAdapter.ShoeViewHolder>() {

    var shoeList: List<ShoeEntry>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeViewHolder = ShoeViewHolder.from(parent)

    override fun onBindViewHolder(holder: ShoeViewHolder, position: Int) = holder.bind(shoeList?.get(position), mListener)

    override fun getItemCount(): Int {
        //If list is null, return 0, otherwise return the size of the list
        return shoeList?.size ?: 0
    }

    class ShoeViewHolder(private val binding: ItemShoeBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(currentShoe: ShoeEntry?, clickListener : ShoeClickListener){
            binding.shoe = currentShoe
            binding.shoeItemClick = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ShoeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil
                    .inflate<ItemShoeBinding>(layoutInflater, R.layout.item_shoe,
                        parent, false)
                return ShoeViewHolder(binding)
            }
        }
    }

    interface ShoeClickListener {
        fun onShoeClicked(chosenShoe: ShoeEntry)
    }

}