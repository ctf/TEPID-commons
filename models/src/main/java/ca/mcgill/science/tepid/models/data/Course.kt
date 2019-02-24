package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Course(@Id val name: String, @Id val season: Season, @Id val year: Int) : TepidJackson, Serializable {
    fun semester() = Semester(season, year)
}

data class Semester(val season: Season, val year: Int) : TepidJackson, Comparable<Semester> {

    override fun compareTo(other: Semester): Int =
            if (year != other.year) year - other.year
            else season.compareTo(other.season)

    companion object {
        /**
         * Get the current semester
         */
        val current: Semester
            get() = with(Calendar.getInstance()) {
                Semester(Season.fromMonth(get(Calendar.MONTH)), get(Calendar.YEAR))
            }

        fun winter(year: Int) = Semester(Season.WINTER, year)
        fun fall(year: Int) = Semester(Season.FALL, year)
        fun summer(year: Int) = Semester(Season.SUMMER, year)
    }
}