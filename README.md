# TEPID Commons

A modularized implementation of sharable features.

More notably:

Jackson models can be found under `:models`

API endpoints can be found under `:retrofit`

Helper functions can be found under `:core`

Courtesy of JitPack, we have a custom []distribution url](https://jitpack.io/#ca.mcgill/tepid-commons)

Use it by adding

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile "ca.mcgill:tepid-commons:$TEPID-COMMONS"
}
```

Where `$TEPID-COMMONS` can be any release, commit hash, or snapshot.

# Testing

Most unit tests are reliant on some sort of authentication.
You may copy over `priv.sample.properties` to `priv.properties` and paste your token there.