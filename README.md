# Rust Analyzer for RustRover

> [!NOTE]
> This plugin is a work-in-progress.
> It may or may not work.
> Use it at your own risk.

[![Build](https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/actions/workflows/build.yaml/badge.svg)][4]
[![Docs](https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/actions/workflows/docs.yaml/badge.svg)][5]
[![Version](https://img.shields.io/jetbrains/plugin/v/25600)][6]
[![Rating](https://img.shields.io/jetbrains/plugin/r/rating/25600)][7]
[![Downloads](https://img.shields.io/jetbrains/plugin/d/25600)][8]

<!-- Plugin description -->

Why pondering between [Rust Analyzer][1] and [RustRover][2]
when you can have both?


## Usage

No configuration is necessary to start using this plugin,
as long as `rust-analyzer` is in your PATH.
The language server will be started automatically on file open.

See [the documentation][3] for more information.


## Logging

You are strongly encouraged to enable language server logging.
This will allow corresponding logs to be recorded in `idea.log`
for further analysis should a problem arises.

Add the following line to the <b>Help</b> |
<b>Diagnostic Tools</b> | <b>Debug Log Settings</b> panel:

```text
com.intellij.platform.lsp
```


  [1]: https://github.com/rust-lang/rust-analyzer
  [2]: https://jetbrains.com/rust
  [3]: https://insyncwithfoo.github.io/rust-analyzer-for-rustrover
<!-- Plugin description end -->


## Installation

This plugin is [available on the Marketplace][6].
You can also download the ZIP files manually from [the <i>Releases</i> tab][9],
[the `build` branch][10] or [the <i>Actions</i> tab][11]
and follow the instructions described [here][12].

Currently supported versions:
2024.2 (build 242.20224.347) - 2024.3.* (build 243.*).


## Credits

Parts of this repository were taken or derived from:

* [@JetBrains/intellij-community][13]
* [@JetBrains/intellij-platform-plugin-template][14]
* [@rust-lang/rust-analyzer][1]


  [4]: https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/actions/workflows/build.yaml
  [5]: https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/actions/workflows/docs.yaml
  [6]: https://plugins.jetbrains.com/plugin/25600/versions
  [7]: https://plugins.jetbrains.com/plugin/25600/reviews
  [8]: https://plugins.jetbrains.com/plugin/25600
  [9]: https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/releases
  [10]: https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/tree/build
  [11]: https://github.com/InSyncWithFoo/rust-analyzer-for-rustrover/actions/workflows/build.yaml
  [12]: https://www.jetbrains.com/help/pycharm/managing-plugins.html#install_plugin_from_disk
  [13]: https://github.com/JetBrains/intellij-community
  [14]: https://github.com/JetBrains/intellij-platform-plugin-template
