package ca.mcgill.science.tepid.models.DTO

data class QuotaData(
    val quota: Int,
    val maxQuota: Int,
    val totalPrinted: Int
)