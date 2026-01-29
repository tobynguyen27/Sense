<p align="center">
    <picture>
        <source media="(prefers-color-scheme: dark)" srcset="assets/sense-logo-dark.png" height="128px">
        <img src="assets/sense-logo-light.png" height="128px">
    </picture>
</p>

<p align="center">
    <a href="https://github.com/tobynguyen27/sense/actions/workflows/build-publish.yml"><img src="https://img.shields.io/github/actions/workflow/status/tobynguyen27/sense/build-publish.yml?style=for-the-badge&logo=github&logoColor=ffffff&labelColor=1675F2&color=eff1f5" /></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/sense"><img src="https://img.shields.io/curseforge/dt/1418112?style=for-the-badge&logo=curseforge&logoColor=ffffff&labelColor=1675F2&color=eff1f5" /></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/sense"><img src="https://img.shields.io/curseforge/v/1418112?style=for-the-badge&logo=curseforge&logoColor=ffffff&labelColor=1675F2&color=eff1f5" /></a>
    <a href="https://discord.gg/RcFhzWGN33"><img src="https://img.shields.io/discord/1466132187286995067?style=for-the-badge&logo=discord&logoColor=ffffff&labelColor=1675F2&color=eff1f5" /></a>
</p>

---

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
