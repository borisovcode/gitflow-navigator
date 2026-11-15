# Marketplace upload sheet

Everything needed to fill the **Upload plugin** form for **Gitflow Navigator 1.0.4**.

> The Marketplace token is **not** stored in this repository and must never be
> committed. Revoke and reissue it in your JetBrains Account after publishing.

## Artifact

| Property | Value |
| --- | --- |
| File | `build/distributions/gitflow-navigator-1.0.4.zip` |
| Size | 150 913 bytes (148 KB) |
| SHA-256 | `a97e55a313a16cf4feac24c777bcfcadaaafddb93f9613d34e76748574d591ae` |
| Plugin ID | `com.borisovcode.gitflow.navigator` |
| Version | `1.0.4` |
| `since-build` | `262` (2026.2) |
| `until-build` | none (no upper bound) |
| Depends on | `com.intellij.modules.vcs`, `com.intellij.tasks`, `Git4Idea` |
| Actions registered | 18 |

## Form fields

| Field | Value |
| --- | --- |
| Name | `Gitflow Navigator` |
| Plugin ID | `com.borisovcode.gitflow.navigator` |
| Vendor | `BorisovCode` |
| Vendor URL | `https://github.com/borisovcode` |
| Category | **Version Control** |
| Tags | `git`, `git-flow`, `gitflow`, `branching`, `workflow`, `vcs` |
| License | **Apache 2.0** (open source) |
| Source code URL | `https://github.com/borisovcode/gitflow-navigator` |
| Issue tracker | `https://github.com/borisovcode/gitflow-navigator/issues` |
| Pricing | **Free** |
| Release channel | **Stable** (do not mark as EAP) |
| Hidden | off |
| Icon | `docs/marketplace/icon-512.png` (also ships `pluginIcon.svg` + `pluginIcon_dark.svg`) |
| Screenshots | `docs/marketplace/screenshots/01-menu.png`, `02-start-feature.png`, `03-options.png` |
| Description | [`DESCRIPTION.md`](DESCRIPTION.md) — 4 227 characters |

## What's new (release notes for 1.0.4)

```text
New plugin icon: two branches (blue and teal) merging into one node, with a
< = > code tag in the centre, on a navy tile. Matching dark-theme icon.
```

## Screenshot URLs (already public, return HTTP 200)

```text
https://raw.githubusercontent.com/borisovcode/gitflow-navigator/main/docs/marketplace/screenshots/01-menu.png
https://raw.githubusercontent.com/borisovcode/gitflow-navigator/main/docs/marketplace/screenshots/02-start-feature.png
https://raw.githubusercontent.com/borisovcode/gitflow-navigator/main/docs/marketplace/screenshots/03-options.png
```

## Pre-flight checklist

- [ ] Logged in to https://plugins.jetbrains.com with the JetBrains Account
- [ ] Email of the account is verified
- [ ] Vendor profile **BorisovCode** selected or created
- [ ] Developer Agreement accepted
- [ ] ZIP uploaded (148 KB, well under the 400 MB limit)
- [ ] Description pasted from [`DESCRIPTION.md`](DESCRIPTION.md)
- [ ] Tags and category set (they drive search filters)
- [ ] License **Apache 2.0** + source code URL filled (required for OSS)
- [ ] Icon and three screenshots attached
- [ ] Hidden flag unchecked, channel **Stable**
- [ ] Submitted for moderation (usually 1–2 business days)

## After approval

- Listing: `https://plugins.jetbrains.com/plugin/<number>/gitflow-navigator`
- Users install it from **Settings → Plugins → Marketplace** — the custom
  repository URL is no longer needed.
- Updates: same **Upload plugin** form → pick the existing plugin → upload the new ZIP.
