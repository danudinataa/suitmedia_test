package com.ramaa.suitmedia_test.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserResponse(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<UserResponseItem?>? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

) : Parcelable

