package ca.mcgill.science.tepid.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import ca.allanwang.kit.props.PropHolder

object PropsURL : PropHolder("config/URL.properties") {
    val TESTING by PropsURL.string("TESTING", "true", errorMessage = "TESTING not set")
    val SERVER_URL_TESTING by PropsURL.string("SERVER_URL_TESTING", errorMessage = "SERVER_URL_TESTING not set")
    val SERVER_URL_PRODUCTION by PropsURL.string("SERVER_URL_PRODUCTION", errorMessage = "SERVER_URL_PRODUCTION not set")
    val WEB_URL_TESTING by PropsURL.string("WEB_URL_TESTING", errorMessage = "WEB_URL_TESTING not set")
    val WEB_URL_PRODUCTION by PropsURL.string("WEB_URL_PRODUCTION", errorMessage = "WEB_URL_PRODUCTION not set")
}

object PropsLDAP : PropHolder ("config/LDAP.properties") {
    val LDAP_ENABLED by string("LDAP_ENABLED", "true", errorMessage = "LDAP_ENABLED not set")
    val LDAP_SEARCH_BASE by PropsLDAP.string("LDAP_SEARCH_BASE", errorMessage = "LDAP_SEARCH_BASE not set")
    val ACCOUNT_DOMAIN by PropsLDAP.string("ACCOUNT_DOMAIN", errorMessage = "ACCOUNT_DOMAIN not set")
    val PROVIDER_URL by PropsLDAP.string("PROVIDER_URL", errorMessage = "PROVIDER_URL not set")
    val SECURITY_PRINCIPAL_PREFIX by PropsLDAP.string("SECURITY_PRINCIPAL_PREFIX", errorMessage = "SECURITY_PRINCIPAL_PREFIX not set")
}

object PropsLDAPResource : PropHolder ("config/LDAPResource") {
    val LDAP_RESOURCE_USER by PropsLDAPResource.string("LDAP_RESOURCE_USER", errorMessage = "LDAP_RESOURCE_USER not set")
    val LDAP_RESOURCE_CREDENTIALS by PropsLDAPResource.string("LDAP_RESOURCE_CREDENTIALS", errorMessage = "LDAP_RESOURCE_CREDENTIALS not set")
}

object PropsLDAPGroups : PropHolder ("config/LDAPGroups") {
    val EXCHANGE_STUDENTS_GROUP_BASE by PropsLDAPGroups.string("EXCHANGE_STUDENTS_GROUP_BASE", errorMessage = "EXCHANGE_STUDENTS_GROUP_BASE not set")
    val EXCHANGE_STUDENTS_GROUP_LOCATION by PropsLDAPGroups.string("EXCHANGE_STUDENTS_GROUP_LOCATION", errorMessage = "EXCHANGE_STUDENTS_GROUP_LOCATION not set")
    val ELDERS_GROUPS by PropsLDAPGroups.string("ELDERS_GROUP", errorMessage = "ELDERS_GROUP not set")
    val CTFERS_GROUPS by PropsLDAPGroups.string("CTFERS_GROUPS", errorMessage = "CTFERS_GROUPS not set")
    val USERS_GROUPS by PropsLDAPGroups.string("USERS_GROUPS", errorMessage = "USERS_GROUPS not set")
}

object PropsDB : PropHolder ("config/DB.properties") {
    val COUCHDB_USERNAME by PropsDB.string("COUCHDB_USERNAME", errorMessage = "COUCHDB_USERNAME not set")
    val COUCHDB_PASSWORD by PropsDB.string ("COUCHDB_PASSWORD", errorMessage = "COUCHDB_PASSWORD not set")
    val COUCHDB_URL by PropsDB.string("COUCHDB_URL", errorMessage = "COUCHDB_URL not set")
}

object PropsScreensaver : PropHolder ("config/screensaver.properties") {
    val OFFICE_REGEX by PropsScreensaver.string("OFFICE_REGEX", errorMessage = "OFFICE_REGEX not set")
    val GRAVATAR_SEARCH_TERMS by PropsScreensaver.string("GRAVATAR_SEARCH_TERMS", errorMessage = "GRAVATAR_SEARCH_TERMS not set")
    val REPORT_MALFUNCTIONING_COMPUTER_TEXT by PropsScreensaver.string("REPORT_MALFUNCTIONING_COMPUTER_TEXT", errorMessage = "REPORT_MALFUNCTIONING_COMPUTER_TEXT not set")
    val BACKGROUND_PICTURE_LOCATION by PropsScreensaver.string("BACKGROUND_PICTURE_LOCATION", errorMessage = "BACKGROUND_PICTURE_LOCATION not set")
    val ANNOUNCEMENT_SLIDE_LOCATION by PropsScreensaver.string("ANNOUNCEMENT_SLIDE_LOCATION", errorMessage = "ANNOUNCEMENT_SLIDE_LOCATION not set")
    val GOOGLE_CUSTOM_SEARCH_KEY by PropsScreensaver.string("GOOGLE_CUSTOM_SEARCH_KEY", errorMessage = "GOOGLE_CUSTOM_SEARCH_KEY not set")
    val ICS_CALENDAR_ADDRESS by PropsScreensaver.string("ICS_CALENDAR_ADDRESS", errorMessage = "ICS_CALENDAR_ADDRESS not set")
}
