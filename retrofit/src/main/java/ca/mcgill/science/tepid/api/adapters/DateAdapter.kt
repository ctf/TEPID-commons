package ca.mcgill.science.tepid.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class DateAdapter {

    companion object {
        val formatter: SimpleDateFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss.SX") }
    }

    @ToJson
    fun toJson(date: Date) = formatter.format(date)

    @FromJson
    fun fromJson(date: String) = Date.from(Instant.now())
//            formatter.parse(date)
}