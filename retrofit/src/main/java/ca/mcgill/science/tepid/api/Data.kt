package ca.mcgill.science.tepid.api

/**
 * Created by Allan Wang on 2017-10-29.
 *
 * More data that didn't exist in tepid models
 */

/**
 * Response when toggling colour printing (also has rev: String but we don't need it)
 */
data class ColorResponse(val ok: Boolean, val id: String)

/**
 * User Query; student info from autoSuggest
 * A shorter version of user
 */
data class UserQuery(val displayName: String, val shortUser: String, val email: String, val colorPrinting: Boolean, val type: String)