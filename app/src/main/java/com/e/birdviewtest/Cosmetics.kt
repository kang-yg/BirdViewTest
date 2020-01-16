package com.e.birdviewtest

class Cosmetics() {
    var id: Int = -1
    var thumbnailImage: String = ""
    val fullImage: String = ""
    var title: String = ""
    var price: String = ""
    var description: String = ""
    var oily_score: Int = -1
    var dry_score: Int = -1
    var sensitive_score: Int = -1

    constructor(
        _id: Int,
        _thumbnailImage: String,
        _title: String,
        _price: String,
        _oily_score: Int,
        _dry_score: Int,
        _sensitive_score: Int
    ) : this() {
        this.id = _id
        this.thumbnailImage = _thumbnailImage
        this.title = _title
        this.price = _price
        this.oily_score = _oily_score
        this.dry_score = _dry_score
        this.sensitive_score = _sensitive_score
    }

    constructor(
        _id: Int,
        _fullImage: String,
        _title: String,
        _price: String,
        _description: String,
        _oily_score: Int = -1,
        _dry_score: Int = -1,
        _sensitive_score: Int = -1
    ) : this() {
        this.id = _id
        this.thumbnailImage = _fullImage
        this.title = _title
        this.price = _price
        this.description = _description
        this.oily_score = _oily_score
        this.dry_score = _dry_score
        this.sensitive_score = _sensitive_score
    }
}