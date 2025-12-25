Sense
---

[![Maven repo](https://maven.tobynguyen.dev/api/badge/latest/releases/dev/tobynguyen27/sense?name=Version)](https://maven.tobynguyen.dev)
[![Build and Publish](https://github.com/tobynguyen27/Sense/actions/workflows/build-publish.yml/badge.svg)](https://github.com/tobynguyen27/Sense/actions/workflows/build-publish.yml)

A mod library designed to simplify mod development. It offers a collection of utilities, enabling developers to create mods more efficiently with cleaner, more concise code.

Basic usage
---

### Build setup

Add a maven repository to your `build.gradle`.

```groovy
maven {
    url "https://maven.tobynguyen.dev/releases"
}
```

Then declare CodeBebeLib as a dependency

```groovy
dependencies {
    modImplementation("dev.tobynguyen27:sense:<version>")
}
```

License
---

Sense is licensed under the MIT license. Check [LICENSE](./LICENSE) for further details.
