package com.binjesus.kfhr_mobile.models

data class RecommendedCertificate(
    val id: Int,
    val certificateName: String,
    val issuingOrganization: String,
    val rewardPoints: Int,
    val organizationWebsite: String,
    val certificatePicture: String
)
