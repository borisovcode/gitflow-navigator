# Changelog

All notable changes to Gitflow Navigator are documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/)
and this project adheres to [Semantic Versioning](https://semver.org/).

## [Unreleased]

## [1.0.5] - 2026-11-15

### Changed
- Vendor name is written **BORISOVCODE** everywhere (plugin descriptor, build
  scripts, release workflow, documentation).

## [1.0.4] - 2026-11-15

### Changed
- Final plugin icon: two branches (blue on top, teal at the bottom) merge into a
  single node, with a `< = >` code tag in the centre, on a navy tile.
- Dark-theme icon uses lifted accents (`pluginIcon_dark.svg`).
- Marketplace icons regenerated (512/128 px, dark 512 px) and the scale-check
  sheet updated; icon sources renamed to `docs/marketplace/icon/merge-flow*.svg`.

## [1.0.3] - 2026-11-15

### Changed
- New plugin icon: a route with two waypoints that ends in a forward arrow,
  on a light tile; the waypoint ring and the arrow head stay legible at 16 px.
- Matching dark-theme icon (`pluginIcon_dark.svg`).
- Marketplace icon regenerated at 512 and 128 px; added a dark 512 px preview.

## [1.0.2] - 2026-11-15

### Added
- Plugin icon (`META-INF/pluginIcon.svg` and `pluginIcon_dark.svg`).
- Marketplace assets: 512/128 px icon, three UI screenshots and a long listing
  description (`docs/marketplace/`, `docs/MARKETPLACE.md`).
- `CHANGELOG.md`.

## [1.0.1] - 2026-11-15

### Added
- Unit tests for CLI edition detection, git-flow-next configuration parsing and
  flag mapping (18 tests).

### Changed
- The options dialog now displays the canonical `--fetch` and `--push` flags;
  they are translated per CLI (git-flow AVH uses `-F`/`-p` where required).
- The status bar widget refreshes itself after the git-flow version check.

## [1.0.0] - 2026-11-14

### Added
- First release of **Gitflow Navigator**, a maintained fork of the abandoned
  *Git Flow Integration* plugin.
- Support for IntelliJ Platform **2026.2** and **2026.3**.
- Support for both **git-flow AVH** and **git-flow-next**:
  - CLI edition detection from `git flow version`;
  - reading of the AVH configuration keys and the git-flow-next
    `gitflow.branch.*` model;
  - per-CLI flag mapping;
  - non-interactive custom initialization for git-flow-next.
- Feature, release, hotfix and bugfix operations (start, finish, publish,
  track), status bar widget, task integration and Gitflow options.
- GitHub Actions workflow that builds and publishes a release (ZIP +
  `updatePlugins.xml`) when a `v*` tag is pushed.

### Changed
- New plugin identity: ID `com.borisovcode.gitflow.navigator`, vendor
  **BORISOVCODE**, action/menu/widget/notification/state identifiers renamed to
  avoid clashing with the original plugin.
- Build migrated to the IntelliJ Platform Gradle Plugin 2.x, Gradle 9.8.1 and a
  Java 25 toolchain.
- All deprecated API usages removed; code ported to the 2026.x platform APIs.

### Fixed
- `Do not call synchronous repository update in EDT` when the Gitflow menu was
  built on a background thread in 2026.3.
- `Access is allowed from Event Dispatch Thread (EDT) only` when resolving the
  current repository from the status bar widget.

### Credits
- Based on *Git Flow Integration* by Opher Vishnia and contributors
  (Apache License 2.0). See [NOTICE](NOTICE).

[Unreleased]: https://github.com/borisovcode/gitflow-navigator/compare/v1.0.5...HEAD
[1.0.5]: https://github.com/borisovcode/gitflow-navigator/compare/v1.0.4...v1.0.5
[1.0.4]: https://github.com/borisovcode/gitflow-navigator/compare/v1.0.3...v1.0.4
[1.0.3]: https://github.com/borisovcode/gitflow-navigator/compare/v1.0.2...v1.0.3
[1.0.2]: https://github.com/borisovcode/gitflow-navigator/compare/v1.0.1...v1.0.2
[1.0.1]: https://github.com/borisovcode/gitflow-navigator/compare/v1.0.0...v1.0.1
[1.0.0]: https://github.com/borisovcode/gitflow-navigator/releases/tag/v1.0.0
