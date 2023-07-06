package com.mine.exclusion.model

data class Response(
	val exclusions: List<List<ExclusionsItemItem?>?>? = null,
	val facilities: List<FacilitiesItem?>? = null
)

data class ExclusionsItemItem(
	val options_id: String? = null,
	val facility_id: String? = null
)

data class OptionsItem(
	val name: String? = null,
	val icon: String? = null,
	val id: String? = null
)

data class FacilitiesItem(
	val name: String? = null,
	val options: List<OptionsItem?>? = null,
	val facility_id: String? = null
)

