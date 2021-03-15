package com.manohar.mobiuscodechallenge.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manohar.mobiuscodechallenge.R
import com.manohar.mobiuscodechallenge.databinding.CouponItemBinding
import com.manohar.mobiuscodechallenge.model.CouponCodeResponseItem

class CouponsAdapter(private val context: Context) :
    RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder>() {

    private var _arrayOfCoupons = ArrayList<CouponCodeResponseItem>()

    fun addItems(array: ArrayList<CouponCodeResponseItem>) {
        _arrayOfCoupons = array
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return _arrayOfCoupons.size
    }

    private fun getItem(position: Int): CouponCodeResponseItem {
        return _arrayOfCoupons[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsViewHolder {
        return CouponsViewHolder(
            CouponItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CouponsViewHolder, position: Int) {
        val couponCodeResponseItem = getItem(position)
        holder.binding.dataModel = couponCodeResponseItem

        holder.binding.tvWagerBonus.text = context.getString(
            R.string.wager_bonus_expiry,
            couponCodeResponseItem.wager_bonus_expiry.toString()
        )
        holder.binding.tvWagerDesc.text = context.getString(
            R.string.wager_release_desc,
            couponCodeResponseItem.wager_to_release_ratio_numerator.toString(),
            couponCodeResponseItem.wager_to_release_ratio_denominator.toString()
        )
        val minimumSlabValue = couponCodeResponseItem.slabs.minByOrNull { it.min }
        val maximumSlabWageredMaxValue = couponCodeResponseItem.slabs.minByOrNull { it.wagered_max }
        val maximumSlabWageredOtcMaxValue = couponCodeResponseItem.slabs.minByOrNull { it.otc_max }
        val maximumSlabWageredPercentageValue =
            couponCodeResponseItem.slabs.minByOrNull { it.wagered_percent }
        val maximumSlabWageredOtcPercentageValue =
            couponCodeResponseItem.slabs.minByOrNull { it.otc_percent }
        val totalWagPercentage = (maximumSlabWageredPercentageValue?.wagered_percent
            ?: 0) + (maximumSlabWageredOtcPercentageValue?.otc_percent ?: 0)
        val totalValue =
            (maximumSlabWageredMaxValue?.wagered_max ?: 0) + (maximumSlabWageredOtcMaxValue?.otc_max
                ?: 0)
        holder.binding.tvMinDeposit.text =
            context.getString(R.string.min_deposit, minimumSlabValue?.min.toString())
        holder.binding.tvMaxSlabWag.text =
            context.getString(
                R.string.valid_upto,
                totalWagPercentage.toString(),
                totalValue.toString()
            )

        val slabsAdapter = SlabsAdapter(context)
        val mLayoutManager = LinearLayoutManager(context)
        holder.binding.rvSlabs.layoutManager = mLayoutManager
        holder.binding.rvSlabs.itemAnimator = DefaultItemAnimator()
        holder.binding.rvSlabs.adapter = slabsAdapter
        slabsAdapter.addItems(couponCodeResponseItem.slabs)
    }

    inner class CouponsViewHolder(var binding: CouponItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
        }
    }
}