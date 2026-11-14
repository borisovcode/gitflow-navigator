# Gitflow Navigator

**A Git Flow UI for JetBrains IDEs.** Run the feature / release / hotfix / bugfix
workflow from your IDE, on top of the `git flow` CLI you already use.

Gitflow Navigator is a maintained fork of the abandoned
[Git Flow Integration](https://github.com/OpherV/gitflow4idea) plugin. It keeps
the familiar UI while supporting current IntelliJ-based IDEs and both major
git-flow CLI implementations.

## Features

- **Feature, release, hotfix and bugfix branches** — start, finish, publish and
  track them from *Git → Gitflow Navigator* or the status bar widget.
- **Status bar widget** shows whether the current repository is git-flow enabled
  and gives one-click access to all operations.
- **Options** for fetch/push on finish, keeping local/remote branches, no-ff
  merges, custom tag messages and “don’t tag”.
- **Task integration** — start/finish a git-flow branch together with an IDE task.
- **Two CLI implementations supported:**
  - [git-flow AVH](https://github.com/petervanderdoes/gitflow-avh) (legacy);
  - [git-flow-next](https://github.com/gittower/git-flow-next) (modern Go implementation).
- **Zero configuration migration** — existing git-flow repositories are detected
  automatically, including repositories initialized with git-flow AVH.

## Supported IDEs

- IntelliJ IDEA 2026.2 / 2026.3
- WebStorm 2026.2 / 2026.3
- PyCharm 2026.2 / 2026.3
- Any other IntelliJ Platform 2026.2+ IDE that bundles Git4Idea and the Tasks plugin.

`since-build=262`; no upper build bound.

## Requirements

A `git flow` CLI must be installed and available on `PATH`:

- **git-flow AVH** 1.12.x, or
- **git-flow-next** 2.x.

## Installation

- **From the IDE:** *Settings → Plugins → Marketplace* → search for
  **Gitflow Navigator**.
- **From disk:** download the release ZIP and use
  *Settings → Plugins → ⚙ → Install Plugin from Disk…*
- **Custom plugin repository:**
  *Settings → Plugins → ⚙ → Manage Plugin Repositories…* →
  `https://github.com/borisovcode/gitflow-navigator/releases/latest/download/updatePlugins.xml`

> If the original *Git Flow Integration* plugin is installed, uninstall it to
> avoid duplicate menu entries. Both plugins can coexist technically, but the
> menus would be duplicated.

## Usage

1. Open a Git repository.
2. If git-flow is not initialized yet, choose **Git → Gitflow Navigator →
   Initialize Gitflow Repository…**.
3. Use the **Gitflow Navigator** menu or the status bar widget to start/finish
   features, releases, hotfixes and bugfixes.

## Credits

- **Opher Vishnia** and contributors — the original *Git Flow Integration*
  (Apache License 2.0), on which this plugin is based.
- **Vincent Driessen** — the git-flow branching model.
- **Peter van der Does** and contributors — git-flow AVH.
- **The Tower team** — git-flow-next.
- **Kirill Likhodedov** — the original git4idea plugin.
- **JetBrains** — the IntelliJ Platform.

This project is not affiliated with or endorsed by the original author or by the
git-flow-next maintainers.

## License

Apache License 2.0. See [LICENSE.txt](LICENSE.txt) and [NOTICE](NOTICE).
