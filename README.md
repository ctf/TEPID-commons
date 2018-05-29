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

