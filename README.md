# TEPID Commons

A modularized implementation of sharable features.

[![](https://jitpack.io/v/ca.mcgill/tepid-commons.svg)](https://jitpack.io/#ca.mcgill/tepid-commons)

More notably:

| Model | Contents |
| --- | --- |
| `:core` | Promise & hashing libraries |
| `:models` | Jackson data models |
| `:retrofit` | Public API endpoints |
| `:test` | Unit test helper (internal) |
| `:utils` | Helper functions and extensions |

Courtesy of JitPack, we have a custom [distribution url](https://jitpack.io/#ca.mcgill/tepid-commons)

Use it by adding

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {

    // to include everything
    compile "ca.mcgill:tepid-commons:$TEPID_COMMONS"
    
    // to include specific module
    compile "ca.mcgill.tepid-commons:models:$TEPID_COMMONS"
}
```

Where `$TEPID_COMMONS` can be any release, commit hash, or snapshot.

# Testing

Most unit tests are reliant on some sort of authentication.
You may copy over `priv.sample.properties` to `priv.properties` and paste your token there.

As all tests requiring private arguments pull form that properties file,
we've allocated some common global variables in our `test` module.

# Configs
The configs defined in here are for the main TEPID server and the TEPID projects.
Configurations are defined in the common config files. The configs use the TEPID standard configuration system. This allows them to be defined once, outside of the project's file tree, and used across all TEPID programs. The Gradle task copyConfigs will copy the configs from the project property "tepid_config_dir", which can be set in a number of ways:
    - As a standard project property
    - As a project property passed by command line ("-Ptepid_config_dir=CONFIG_DIR")
    - As an environment variable "tepid_config_dir"
A lookup for the environment variable only occurs if the project property is not defined in one of the other two ways.

The configs are explained as follows:
- [DB](#db.properties)
- [LDAP](#LDAP.properties)
- [LDAP Groups](#LDAPGroups.properties)
- [LDAP Resource Account](#LDAPResource.properties)
- [LDAP Test Account](#LDAPTest.properties)
- [Screensaver](#screensaver.properties)
- [TEPID Endpoint Manager](#TEM.properties)
- [URLs](#URL.properties)


## DB.properties
- COUCHDB_USERNAME : the username for the database user
- COUCHDB_PASSWORD : the password for the database user
- COUCHDB_URL : the url of the couchdb database, like http://testpid.example.com:5984/tepid-clone

## LDAP.properties
General properties relating to the LDAP domain itself
- LDAP_ENABLED : currently only TRUE is supported, but it's nice to dream
- LDAP_SEARCH_BASE : the "dc=..." part of the locator
- ACCOUNT_DOMAIN : the "@example.com" part of your domain accounts
- PROVIDER_URL : the origin of the LDAP server (ldap://us.example.com:389)
- SECURITY_PRINCIPAL_PREFIX : the domain identifier thing before your domain account's actual name, like "DOMAIN\" in "DOMAIN\jdoe123"

## LDAPGroups.properties
The permissions levels are explained further in the server component. Briefly : Elders are admins; CTFers are worker bees (1st tier support, lab managers) who need to refund people or mark printers as down (for example); Users are users, who are able to print; Exchange Students are also users, but limited to printing for a certain time period. Note that multiple groups can be specified by separating the groups with any of the illegal LDAP characters ( ,+"\<>;= )
- GROUPS_LOCATION : the location of the LDAP groups
- EXCHANGE_STUDENTS_GROUP_BASE : the base name of group for LDAP access for exchange students. Do be sure to create the groups as time moves on.
- ELDERS_GROUP : LDAP groups for Elders
- CTFERS_GROUPS : LDAP groups for CTFers
- USERS_GROUPS : LDAP groups for Users


## LDAPResource.properties
TEPID uses a resource account to do critical stuff as well as fun stuff like username search.
- LDAP_RESOURCE_USER : the username of the LDAP resource account
- LDAP_RESOURCE_CREDENTIALS : the password of the LDAP resource account

## LDAPTest.properties
Information for testing LDAP functionality.
TEST_USER : the username of the user to be used for testing
TEST_PASSWORD : the password for the user to be used for testing

## screensaver.properties
Properties used to configure the screensaver
- OFFICE_REGEX : regex string to match office computers, which will have office relevant options like an avatar and internal announcements
- GRAVATAR_SEARCH_TERMS : search terms to help narrow down the results for the avatar picture in case a gravatar is not found, such as your organisation name
- GOOGLE_CUSTOM_SEARCH_KEY : the custom part of the url containing your key and the cx
- ICS_CALENDAR_ADDRESS : address of ics to pull events from
- REPORT_MALFUNCTIONING_COMPUTER_TEXT : completes the sentence to "Report this malfunctioning computer to", displayed when a computer cannot contact the network
- BACKGROUND_PICTURE_LOCATION : location for the background picture
- ANNOUNCEMENT_SLIDE_LOCATION : directory containing the announcement slides

## TEM.properties
Properties for the TEPID Endpoint Manager, which is currently a work in progress
- TEM_URL : the URL for the TEM server

## URL.properties
- Testing : (boolean) chooses between the testing and production URL
Both of these options have testing and production
- Server URL : the URL for the application root of the server with all the rest endpoints
- Web URL : the URL for the web server, typically the same but without the slug targetting the tomcat webapp