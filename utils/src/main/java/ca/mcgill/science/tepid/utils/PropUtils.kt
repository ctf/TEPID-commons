package ca.mcgill.science.tepid.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import ca.allanwang.kit.props.PropHolder

object PropsLDAP : PropHolder ("ldap.properties", "LDAP.properties", "../ldap.properties", "../LDAP.properties") {
    val LDAP_SEARCH_BASE by PropsLDAP.string("LDAP_SEARCH_BASE", errorMessage = "LDAP_SEARCH_BASE not set")
    val ACCOUNT_DOMAIN by PropsLDAP.string("ACCOUNT_DOMAIN", errorMessage = "ACCOUNT_DOMAIN not set")
    val PROVIDER_URL by PropsLDAP.string("PROVIDER_URL", errorMessage = "PROVIDER_URL not set")
    val SECURITY_PRINCIPAL_PREFIX by PropsLDAP.string("SECURITY_PRINCIPAL_PREFIX", errorMessage = "SECURITY_PRINCIPAL_PREFIX not set")
}