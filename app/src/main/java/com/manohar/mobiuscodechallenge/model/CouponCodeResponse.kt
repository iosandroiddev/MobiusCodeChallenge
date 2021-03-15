package com.manohar.mobiuscodechallenge.model

class CouponCodeResponse : ArrayList<CouponCodeResponseItem>()

/**
 *
 *
 * @property _cls
 * @property bonus_booster
 * @property bonus_image_back
 * @property bonus_image_front
 * @property campaign
 * @property code
 * @property created_at
 * @property days_since_last_purchase_min
 * @property eligibility_user_levels
 * @property eligibility_user_segments
 * @property id
 * @property is_active
 * @property is_bonus_booster_enabled
 * @property is_deleted
 * @property last_updated_at
 * @property ribbon_msg
 * @property slabs
 * @property tab_type
 * @property tags
 * @property user_limit
 * @property user_redeem_limit
 * @property user_segmentation_type
 * @property valid_from
 * @property valid_until
 * @property visibility_user_levels
 * @property visibility_user_segments
 * @property wager_bonus_expiry
 * @property wager_to_release_ratio_denominator
 * @property wager_to_release_ratio_numerator
 */
data class CouponCodeResponseItem(
    val _cls: String,
    val bonus_booster: String,
    val bonus_image_back: String,
    val bonus_image_front: String,
    val campaign: String,
    val code: String,
    val created_at: String,
    val days_since_last_purchase_min: Int,
    val eligibility_user_levels: List<Int>,
    val eligibility_user_segments: List<String>,
    val id: String,
    val is_active: Boolean,
    val is_bonus_booster_enabled: Boolean,
    val is_deleted: Boolean,
    val last_updated_at: String,
    val ribbon_msg: String,
    val slabs: ArrayList<Slab>,
    val tab_type: String,
    val tags: Tags,
    val user_limit: Int,
    val user_redeem_limit: Int,
    val user_segmentation_type: String,
    val valid_from: String,
    val valid_until: String,
    val visibility_user_levels: List<Int>,
    val visibility_user_segments: List<String>,
    val wager_bonus_expiry: Int,
    val wager_to_release_ratio_denominator: Int,
    val wager_to_release_ratio_numerator: Int
)

/**
 *
 * @property max
 * @property min
 * @property name
 * @property num
 * @property otc_max
 * @property otc_percent
 * @property wagered_max
 * @property wagered_percent
 */
data class Slab(
    val max: Int,
    val min: Int,
    val name: String,
    val num: Int,
    val otc_max: Int,
    val otc_percent: Int,
    val wagered_max: Int,
    val wagered_percent: Int
)

class Tags(
)