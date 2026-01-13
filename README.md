<p align="center">
    <img src="./src/main/resources/assets/sense/icon.png" width="150">
</p>

<h1 align="center">Sense</h1>

<p align="center">
    <a href="https://maven.tobynguyen.dev"><img src="https://maven.tobynguyen.dev/api/badge/latest/releases/dev/tobynguyen27/sense?name=Version"></a>
    <a href="https://github.com/tobynguyen27/Sense/actions/workflows/build-publish.yml"><img src="https://github.com/tobynguyen27/Sense/actions/workflows/build-publish.yml/badge.svg"></a>
</p>

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
