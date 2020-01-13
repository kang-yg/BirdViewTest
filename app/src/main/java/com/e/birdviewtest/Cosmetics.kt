package com.e.birdviewtest

class Cosmetics(
    _id: Int,
    _thumbnailImage: String,
    _title: String,
    _price: String,
    _oily_score: Int = -1,
    _dry_score: Int = -1,
    _sensitive_score : Int = -1
) {
    var id: Int = _id
    var thumbnailImage: String = _thumbnailImage
    var title: String = _title
    var price: String = _price
    var oily_score: Int = _oily_score
    var dry_score: Int = _dry_score
    var sensitive_score: Int = _sensitive_score
}