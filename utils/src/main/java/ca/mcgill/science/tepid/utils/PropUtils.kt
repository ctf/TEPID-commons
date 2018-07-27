package ca.mcgill.science.tepid.utils



/**
 * Configurations for any TEPID project. This way, sharing config interfaces is the default action.
 * The configs are loaded triply lazily: The config objects will only be built on first use; the PropLoaders are stored in a lazy property; and the individual properties are lazy themselves.
 * The DefaultProps generator can be changed before any initialisation happens
 * ===ADDING A NEW CONFIG===
 * When you add a new config here. make sure to document it in the README as well!
* */

object DefaultProps{
    var withName: (String) -> List<PropLoader> =  {fileName -> listOf(
            FilePropLoader(fileName),
            JarPropLoader("/$fileName")
    )}
}

object PropsAbout : PropHolder(DefaultProps.withName("creationInformation.properties")) {
    val LINK_MAIN by PropsAbout.get("LINK_MAIN")
    val LINK_TOS by PropsAbout.get("LINK_TOS")
    val ORG_NAME by PropsAbout.get("ORG_NAME")
}

object PropsCreationInfo : PropHolder(DefaultProps.withName("creationInformation.properties")) {
    val HASH by PropsCreationInfo.get("HASH")
    val TAG by PropsCreationInfo.get("TAG")
    val CREATION_TIMESTAMP by PropsCreationInfo.get("CREATION_TIMESTAMP")
    val CREATION_TIME by PropsCreationInfo.get("CREATION_TIME")
}

object PropsURL : PropHolder(DefaultProps.withName("URL.properties")) {
    val TESTING by PropsURL.get("TESTING")
    val SERVER_URL_TESTING by PropsURL.get("SERVER_URL_TESTING")
    val SERVER_URL_PRODUCTION by PropsURL.get("SERVER_URL_PRODUCTION")
    val WEB_URL_TESTING by PropsURL.get("WEB_URL_TESTING")
    val WEB_URL_PRODUCTION by PropsURL.get("WEB_URL_PRODUCTION")
}

object PropsLDAP : PropHolder (DefaultProps.withName("LDAP.properties")) {
    val LDAP_ENABLED by PropsLDAP.get("LDAP_ENABLED")
    val LDAP_SEARCH_BASE by PropsLDAP.get("LDAP_SEARCH_BASE")
    val ACCOUNT_DOMAIN by PropsLDAP.get("ACCOUNT_DOMAIN")
    val PROVIDER_URL by PropsLDAP.get("PROVIDER_URL")
    val SECURITY_PRINCIPAL_PREFIX by PropsLDAP.get("SECURITY_PRINCIPAL_PREFIX")
}

object PropsLDAPResource : PropHolder (DefaultProps.withName("LDAPResource.properties")) {
    val LDAP_RESOURCE_USER by PropsLDAPResource.get("LDAP_RESOURCE_USER")
    val LDAP_RESOURCE_CREDENTIALS by PropsLDAPResource.get("LDAP_RESOURCE_CREDENTIALS")
}

object PropsLDAPGroups : PropHolder (DefaultProps.withName("LDAPGroups.properties")) {
    val EXCHANGE_STUDENTS_GROUP_BASE by PropsLDAPGroups.get("EXCHANGE_STUDENTS_GROUP_BASE")
    val GROUPS_LOCATION by PropsLDAPGroups.get("GROUPS_LOCATION")
    val ELDERS_GROUPS by PropsLDAPGroups.get("ELDERS_GROUPS")
    val CTFERS_GROUPS by PropsLDAPGroups.get("CTFERS_GROUPS")
    val USERS_GROUPS by PropsLDAPGroups.get("USERS_GROUPS")
}

object PropsLDAPTestUser : PropHolder (DefaultProps.withName("LDAPTestUser.properties")) {
    val TEST_USER by PropsLDAPTestUser.get("TEST_USER")
    val TEST_PASSWORD by PropsLDAPTestUser.get("TEST_PASSWORD")
}

object PropsDB : PropHolder (DefaultProps.withName("DB.properties")) {
    val COUCHDB_USERNAME by PropsDB.get("COUCHDB_USERNAME")
    val COUCHDB_PASSWORD by PropsDB.get ("COUCHDB_PASSWORD")
    val COUCHDB_URL by PropsDB.get("COUCHDB_URL")
}

object PropsTEM : PropHolder(DefaultProps.withName("TEM.properties")){
    val TEM_URL by PropsTEM.get("TEM_URL")
}

object PropsBarcode : PropHolder(DefaultProps.withName("barcode.properties")){
    val BARCODES_URL by PropsBarcode.get("BARCODES_URL")
    val BARCODES_DB_USERNAME by PropsBarcode.get("BARCODES_DB_URL")
    val BARCODES_DB_PASSWORD by PropsBarcode.get("BARCODES_DB_PASSWORD")
}

object PropsScreensaver : PropHolder (DefaultProps.withName("screensaver.properties")) {
    val OFFICE_REGEX by PropsScreensaver.get("OFFICE_REGEX")
    val GRAVATAR_SEARCH_TERMS by PropsScreensaver.get("GRAVATAR_SEARCH_TERMS")
    val REPORT_MALFUNCTIONING_COMPUTER_TEXT by PropsScreensaver.get("REPORT_MALFUNCTIONING_COMPUTER_TEXT")
    val BACKGROUND_PICTURE_LOCATION by PropsScreensaver.get("BACKGROUND_PICTURE_LOCATION")
    val ANNOUNCEMENT_SLIDE_LOCATION by PropsScreensaver.get("ANNOUNCEMENT_SLIDE_LOCATION")
    val GOOGLE_CUSTOM_SEARCH_KEY by PropsScreensaver.get("GOOGLE_CUSTOM_SEARCH_KEY")
    val ICS_CALENDAR_ADDRESS by PropsScreensaver.get("ICS_CALENDAR_ADDRESS")
}
