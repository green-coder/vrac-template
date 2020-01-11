# vrac-template

A template to scaffold a Shadow-CLJS project which uses Vrac.

## While dev on the template

```shell script
clj -m clj-new.create vrac group-id/project-name
```

## Usage

First, prepare your `~/.clojure/deps.edn` [accordingly](https://github.com/seancorfield/clj-new#getting-started).

Then, to create a new Vrac project:

```shell script
clj -A:new vrac group-id/project-name -o dir-name
```

## Thanks

Big thank to Sean Corfield for implementing [clj-new](https://github.com/seancorfield/clj-new).

## License

Copyright © 2020 Vincent Cantin

Distributed under the Eclipse Public License either version 2.0 or (at your option) any later version.
