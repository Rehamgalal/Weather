package com.scan.data.models

data class CitiesResponse(
    val `data`: List<Data>,
    val meta: Meta
) {
    data class Data(
        val address: Address,
        val geoCode: GeoCode,
        val iataCode: String,
        val name: String,
        val subType: String,
        val type: String
    ) {
        data class Address(
            val countryCode: String,
            val stateCode: String
        )

        data class GeoCode(
            val latitude: Double,
            val longitude: Double
        )
    }

    data class Meta(
        val count: Int,
        val links: Links
    ) {
        data class Links(
            val self: String
        )
    }
}