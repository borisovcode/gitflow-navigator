# Marketplace assets

Assets for the JetBrains Marketplace listing of **Gitflow Navigator**.

| File | Purpose |
| --- | --- |
| `icon-512.png`, `icon-128.png` | Plugin icon for the listing, rendered from `src/main/resources/META-INF/pluginIcon.svg` |
| `icon-dark-512.png` | Dark-theme icon preview, rendered from `pluginIcon_dark.svg` |
| `icon/merge-flow.svg`, `icon/merge-flow-dark.svg` | Source SVG of the icon (same files as in `META-INF`) |
| `icon/preview.png` | Scale check: 16 / 32 / 64 px plus 128 px light and dark |
| `screenshots/01-menu.png` | The **Git → Gitflow Navigator** menu |
| `screenshots/02-start-feature.png` | The **New Feature…** dialog |
| `screenshots/03-options.png` | **Settings → Gitflow Navigator** |
| [`../MARKETPLACE.md`](../MARKETPLACE.md) | Long description for the listing |

> The screenshots are representative 1280×800 UI mockups in the Darcula theme.
> Replace them with real IDE screenshots before publishing if you prefer.

## Marketplace checklist

- [ ] Vendor profile: **BorisovCode**
- [ ] Plugin icon: `icon-512.png` (the plugin also ships
      `META-INF/pluginIcon.svg` and `META-INF/pluginIcon_dark.svg`)
- [ ] Description: [`../MARKETPLACE.md`](../MARKETPLACE.md)
- [ ] Screenshots: `screenshots/01-menu.png`, `02-start-feature.png`, `03-options.png`
- [ ] License: Apache 2.0
- [ ] Source code URL: https://github.com/borisovcode/gitflow-navigator
- [ ] Issue tracker URL: https://github.com/borisovcode/gitflow-navigator/issues
- [ ] Changelog: [`../../CHANGELOG.md`](../../CHANGELOG.md)
- [ ] Tags: `VCS`, `Git`, `Workflow`
- [ ] Compatibility: 2026.2+ (`since-build=262`)

## Suggested short summary

> A Git Flow UI for IntelliJ IDEA, WebStorm and PyCharm. Supports both git-flow
> AVH and git-flow-next. A maintained fork of the abandoned Git Flow Integration
> plugin.
