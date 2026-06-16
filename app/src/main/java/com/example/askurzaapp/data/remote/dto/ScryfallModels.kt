package com.example.askurzaapp.data.remote.dto

data class ScryfallCard(
    val id: String,
    val multiverse_ids: List<Int>?,
    val name: String,
    val type_line: String,
    val image_uris: ImageUris?,
    val scryfall_uri: String,
    val mana_cost: String?,
    val oracle_text: String?,
    val loyalty: String?
)

data class ImageUris(
    val normal: String?,
    val large: String?,
    val png: String?
)

data class ScryfallResponse(
    val data: List<ScryfallCard>,
    val has_more: Boolean,
    val next_page: String?
)
