package com.manohar.mobiuscodechallenge.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manohar.mobiuscodechallenge.R
import com.manohar.mobiuscodechallenge.databinding.SlabsItemBinding
import com.manohar.mobiuscodechallenge.model.Slab

class SlabsAdapter(private val context: Context) :
    RecyclerView.Adapter<SlabsAdapter.SlabsViewHolder>() {

    private var _arrayOfSlabs = ArrayList<Slab>()


    fun addItems(array: ArrayList<Slab>) {
        _arrayOfSlabs = array
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return _arrayOfSlabs.size
    }

    private fun getItem(position: Int): Slab {
        return _arrayOfSlabs[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlabsViewHolder {
        return SlabsViewHolder(
            SlabsItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SlabsViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.tvPurchaseAmountValue.text = context.getString(
                R.string.purchase_amount_value,
                data.min.toString(),
                data.max.toString()
            )
        holder.binding.tvBonusAmountValue.text = context.getString(
                R.string.slab_amount,
                data.wagered_percent.toString(),
                data.wagered_max.toString()
            )
        holder.binding.tvInstantCashValue.text =
            context.getString(
                R.string.slab_amount,
                data.otc_percent.toString(),
                data.otc_max.toString()
            )

    }

    inner class SlabsViewHolder(var binding: SlabsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
        }
    }
}