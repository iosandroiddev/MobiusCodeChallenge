package com.manohar.mobiuscodechallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.manohar.mobiuscodechallenge.R
import com.manohar.mobiuscodechallenge.databinding.CouponItemBinding
import com.manohar.mobiuscodechallenge.model.CouponCodeResponse
import com.manohar.mobiuscodechallenge.model.CouponCodeResponseItem
import com.manohar.mobiuscodechallenge.ui.adapter.SlabsAdapter
import com.manohar.mobiuscodechallenge.viewmodel.BonusViewModel


/**
 * This will display only the second Item from the list of Coupon Items.
 *
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var _couponsViewModel: BonusViewModel
    private lateinit var _detailBinding: CouponItemBinding

    private lateinit var _slabsAdapter: SlabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _detailBinding = DataBindingUtil.setContentView(this, R.layout.coupon_item)
        initializeViewModel()
        setUpRecyclerView()
        _couponsViewModel.fetchBonusCodes()
    }

    /**
     * Binding Recycler View.
     */
    private fun setUpRecyclerView() {
        _slabsAdapter = SlabsAdapter(this)
        val mLayoutManager = LinearLayoutManager(this)
        _detailBinding.rvSlabs.layoutManager = mLayoutManager
        _detailBinding.rvSlabs.itemAnimator = DefaultItemAnimator()
        _detailBinding.rvSlabs.adapter = _slabsAdapter
    }

    /**
     * Initializing View Model & Observing View Model
     *
     */
    private fun initializeViewModel() {
        _couponsViewModel = ViewModelProvider(this).get(BonusViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        _couponsViewModel.apiErrorResponse.observe(this, { apiErrorMessage ->
            apiErrorMessage?.let {
                if (it.isNotEmpty()) {
                    // Display an Error Message with snackbar.
                    Snackbar.make(_detailBinding.detailContent, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        _couponsViewModel.apiLoadingIndicator.observe(this, { loadingIndicator ->
            loadingIndicator?.let {
                if (it) {
                    // Show Loading Indicator
                } else {
                    // Hide Loading Indicator
                }
            }
        })

        _couponsViewModel.couponCodesResponse.observe(this, { apiResponse ->
            apiResponse?.let { arrayOfCouponCodes ->
                arrayOfCouponCodes?.let {
                    handleAllCoupons(it)
                }
            }

        })

    }

    /**
     * Method to display the Coupon Item
     *
     * @param couponCodeResponse
     */
    private fun handleAllCoupons(couponCodeResponse: CouponCodeResponse) {
        if (couponCodeResponse.size > 2) {
            _detailBinding.dataModel = couponCodeResponse[1]
            bindData(couponCodeResponse[1])
        } else if (couponCodeResponse.size > 1) {
            _detailBinding.dataModel = couponCodeResponse[0]
            bindData(couponCodeResponse[0])
        }
    }

    /**
     * Bind Api Data to the UI
     *
     * @param couponCodeResponseItem
     */
    private fun bindData(couponCodeResponseItem: CouponCodeResponseItem) {
        _slabsAdapter.addItems(couponCodeResponseItem.slabs)
        _detailBinding.tvWagerBonus.text = getString(
            R.string.wager_bonus_expiry,
            couponCodeResponseItem.wager_bonus_expiry.toString()
        )
        _detailBinding.tvWagerDesc.text = getString(
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

        _detailBinding.tvMinDeposit.text =
            getString(R.string.min_deposit, minimumSlabValue?.min.toString())

        _detailBinding.tvMaxSlabWag.text =
            getString(R.string.valid_upto, totalWagPercentage.toString(), totalValue.toString())

    }
}