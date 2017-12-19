# TEPID Commons

A modularized implementation of sharable features.

More notably:

| Model | Contents |
| --- | --- |
| `:core` | Promise & hashing libraries |
| `:ldap` | User query through McGill's LDAP |
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
    compile "ca.mcgill:tepid-commons:models:$TEPID_COMMONS"
}
```

Where `$TEPID_COMMONS` can be any release, commit hash, or snapshot.

# Testing

Most unit tests are reliant on some sort of authentication.
You may copy over `priv.sample.properties` to `priv.properties` and paste your token there.

As all tests requiring private arguments pull form that properties file,
we've allocated some common global variables in our `test` module.
