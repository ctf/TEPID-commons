package ca.mcgill.science.tepid.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import ca.allanwang.kit.props.PropHolder

var configLocations = mutableListOf<String>("")

fun inAllLocations (item: String): Array<String> {
    return configLocations.map{ it.plus(item)}.toTypedArray()
}

object PropsCreationInfo : PropHolder(*inAllLocations("creationInformation.properties")) {
    val HASH by PropsCreationInfo.string("HASH", errorMessage = "HASH not set")
    val TAG by PropsCreationInfo.string("TAG", errorMessage = "TAG not set")
    val CREATION_TIMESTAMP by PropsCreationInfo.string("CREATION_TIMESTAMP", errorMessage = "CREATION_TIMESTAMP not set")
    val CREATION_TIME by PropsCreationInfo.string("CREATION_TIME", errorMessage = "CREATION_TIME not set")
}

object PropsURL : PropHolder(*inAllLocations("URL.properties")) {
    val TESTING by PropsURL.string("TESTING", "true", errorMessage = "TESTING not set")
    val SERVER_URL_TESTING by PropsURL.string("SERVER_URL_TESTING", errorMessage = "SERVER_URL_TESTING not set")
    val SERVER_URL_PRODUCTION by PropsURL.string("SERVER_URL_PRODUCTION", errorMessage = "SERVER_URL_PRODUCTION not set")
    val WEB_URL_TESTING by PropsURL.string("WEB_URL_TESTING", errorMessage = "WEB_URL_TESTING not set")
    val WEB_URL_PRODUCTION by PropsURL.string("WEB_URL_PRODUCTION", errorMessage = "WEB_URL_PRODUCTION not set")
}

object PropsLDAP : PropHolder (*inAllLocations("LDAP.properties")) {
    val LDAP_ENABLED by string("LDAP_ENABLED", "true", errorMessage = "LDAP_ENABLED not set")
    val LDAP_SEARCH_BASE by PropsLDAP.string("LDAP_SEARCH_BASE", errorMessage = "LDAP_SEARCH_BASE not set")
    val ACCOUNT_DOMAIN by PropsLDAP.string("ACCOUNT_DOMAIN", errorMessage = "ACCOUNT_DOMAIN not set")
    val PROVIDER_URL by PropsLDAP.string("PROVIDER_URL", errorMessage = "PROVIDER_URL not set")
    val SECURITY_PRINCIPAL_PREFIX by PropsLDAP.string("SECURITY_PRINCIPAL_PREFIX", errorMessage = "SECURITY_PRINCIPAL_PREFIX not set")
}

object PropsLDAPResource : PropHolder (*inAllLocations("LDAPResource.properties")) {
    val LDAP_RESOURCE_USER by PropsLDAPResource.string("LDAP_RESOURCE_USER", errorMessage = "LDAP_RESOURCE_USER not set")
    val LDAP_RESOURCE_CREDENTIALS by PropsLDAPResource.string("LDAP_RESOURCE_CREDENTIALS", errorMessage = "LDAP_RESOURCE_CREDENTIALS not set")
}

object PropsLDAPGroups : PropHolder (*inAllLocations("LDAPGroups.properties")) {
    val EXCHANGE_STUDENTS_GROUP_BASE by PropsLDAPGroups.string("EXCHANGE_STUDENTS_GROUP_BASE", errorMessage = "EXCHANGE_STUDENTS_GROUP_BASE not set")
    val EXCHANGE_STUDENTS_GROUP_LOCATION by PropsLDAPGroups.string("EXCHANGE_STUDENTS_GROUP_LOCATION", errorMessage = "EXCHANGE_STUDENTS_GROUP_LOCATION not set")
    val ELDERS_GROUPS by PropsLDAPGroups.string("ELDERS_GROUPS", errorMessage = "ELDERS_GROUPS not set")
    val CTFERS_GROUPS by PropsLDAPGroups.string("CTFERS_GROUPS", errorMessage = "CTFERS_GROUPS not set")
    val USERS_GROUPS by PropsLDAPGroups.string("USERS_GROUPS", errorMessage = "USERS_GROUPS not set")
}

object PropsLDAPTestUser : PropHolder (*inAllLocations("LDAPTestUser.properties")) {
    val TEST_USER by PropsLDAPTestUser.string("TEST_USER", errorMessage = "TEST_USER not set")
    val TEST_PASSWORD by PropsLDAPTestUser.string("TEST_PASSWORD", errorMessage = "TEST_PASSWORD not set")
}

object PropsDB : PropHolder (*inAllLocations("DB.properties")) {
    val COUCHDB_USERNAME by PropsDB.string("COUCHDB_USERNAME", errorMessage = "COUCHDB_USERNAME not set")
    val COUCHDB_PASSWORD by PropsDB.string ("COUCHDB_PASSWORD", errorMessage = "COUCHDB_PASSWORD not set")
    val COUCHDB_URL by PropsDB.string("COUCHDB_URL", errorMessage = "COUCHDB_URL not set")
}

object PropsTEM : PropHolder(*inAllLocations("TEM.properties")){
    val TEM_URL by PropsTEM.string("TEM_URL", errorMessage = "TEM_URL not set")
}

object PropsBarcode : PropHolder(*inAllLocations("barcode.properties")){
    val BARCODES_URL by PropsBarcode.string("BARCODES_URL", errorMessage = "BARCODES_URL not set")
    val BARCODES_DB_USERNAME by PropsBarcode.string("BARCODES_DB_URL", errorMessage = "BARCODES_DB_URL not set")
    val BARCODES_DB_PASSWORD by PropsBarcode.string("BARCODES_DB_PASSWORD", errorMessage = "BARCODES_DB_PASSWORD not set")
}

object PropsScreensaver : PropHolder (*inAllLocations("screensaver.properties")) {
    val OFFICE_REGEX by PropsScreensaver.string("OFFICE_REGEX", errorMessage = "OFFICE_REGEX not set")
    val GRAVATAR_SEARCH_TERMS by PropsScreensaver.string("GRAVATAR_SEARCH_TERMS", errorMessage = "GRAVATAR_SEARCH_TERMS not set")
    val REPORT_MALFUNCTIONING_COMPUTER_TEXT by PropsScreensaver.string("REPORT_MALFUNCTIONING_COMPUTER_TEXT", errorMessage = "REPORT_MALFUNCTIONING_COMPUTER_TEXT not set")
    val BACKGROUND_PICTURE_LOCATION by PropsScreensaver.string("BACKGROUND_PICTURE_LOCATION", errorMessage = "BACKGROUND_PICTURE_LOCATION not set")
    val ANNOUNCEMENT_SLIDE_LOCATION by PropsScreensaver.string("ANNOUNCEMENT_SLIDE_LOCATION", errorMessage = "ANNOUNCEMENT_SLIDE_LOCATION not set")
    val GOOGLE_CUSTOM_SEARCH_KEY by PropsScreensaver.string("GOOGLE_CUSTOM_SEARCH_KEY", errorMessage = "GOOGLE_CUSTOM_SEARCH_KEY not set")
    val ICS_CALENDAR_ADDRESS by PropsScreensaver.string("ICS_CALENDAR_ADDRESS", errorMessage = "ICS_CALENDAR_ADDRESS not set")
}
