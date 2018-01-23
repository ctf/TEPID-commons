# Gradle Plugin

Version updates for TEPID dependencies

## Contents
* [Versions](#versions)
* [Plugins](#plugins)
* [Dependencies](#dependencies)
* [Changelog Generator](#changelog-generator)

## Usage

Firstly, add the plugin to the buildscript:

```gradle
buildscript {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }

    dependencies {
        ...
        classpath "ca.mcgill:tepid-commons:$TEPID_COMMONS"
    }
}
```

Then where necessary, apply the plugin using

```gradle
apply plugin: 'ca.mcgill.science.tepid.commons'
```

# Versions

> [Versions.groovy](/buildSrc/src/main/groovy/ca/allanwang/kau/Versions.groovy)

Contains the version code for any external library used in KAU.
You are free to use the values through `kau.[tagName]`.

As an example, AppCompat is imported in KAU using

```gradle
api "com.android.support:appcompat-v7:${kau.supportLibs}"
```

# Plugins

> [Plugins.groovy](/buildSrc/src/main/groovy/ca/allanwang/kau/Plugins.groovy)

Unfortunately, it seems like you can't use the plugin directly in the buildscript, so this is mainly internal.

The plugins data, found using `kauPlugins.[tagName]` contains a collection of useful plugin classpaths.
The versions are taken from `Versions.groovy`, so it is always in sync.

# Dependencies

> [Dependencies.groovy](/buildSrc/src/main/groovy/ca/allanwang/kau/Dependencies.groovy)

Contains the dependency string for common libraries.
You are free to use the values through `kauDependency.[tagName]`.

As an example, adding junit can be done through

```gradle
testImplementation kauDependency.junit
```

# Changelog Generator

In conjunction with [core](/core#changelog-xml), 
the xml changelog can be converted to markdown.

To allow for compilation per build, add your own task:

```gradle
task generateChangelogMd() {
    kauChangelog.generate([inputPath], [outputPath])
}
```

The wrapper allows for the generator to be called automatically with each build.



