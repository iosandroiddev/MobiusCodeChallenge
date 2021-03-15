package com.manohar.mobiuscodechallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.manohar.mobiuscodechallenge.R
import com.manohar.mobiuscodechallenge.model.CouponCodeResponse
import com.manohar.mobiuscodechallenge.ui.adapter.CouponsAdapter
import com.manohar.mobiuscodechallenge.ui.adapter.SlabsAdapter
import com.manohar.mobiuscodechallenge.viewmodel.BonusViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This will display all the coupons.
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var _couponsViewModel: BonusViewModel
    private lateinit var _couponsAdapter: CouponsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViewModel()
        setUpRecyclerView()
        _couponsViewModel.fetchBonusCodes()
    }

    private fun initializeViewModel() {
        _couponsViewModel = ViewModelProvider(this).get(BonusViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        _couponsViewModel.apiErrorResponse.observe(this, { apiErrorMessage ->
            apiErrorMessage?.let {
                if (it.isNotEmpty()) {
                    // Display an Error Message with snackbar.
                    Snackbar.make(mainContent, it, Snackbar.LENGTH_SHORT).show()
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
     * Binding Recycler View.
     */
    private fun setUpRecyclerView() {
        _couponsAdapter = CouponsAdapter(this)
        val mLayoutManager = LinearLayoutManager(this)
        rvCoupons.layoutManager = mLayoutManager
        rvCoupons.itemAnimator = DefaultItemAnimator()
        rvCoupons.adapter = _couponsAdapter
    }

    /**
     * @param couponCodeResponse
     */
    private fun handleAllCoupons(couponCodeResponse: CouponCodeResponse) {
        _couponsAdapter.addItems(couponCodeResponse)
    }

}