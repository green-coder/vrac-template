# vrac-template

A template to scaffold a Shadow-CLJS project which uses Vrac.

## Usage

First, prepare your `~/.clojure/deps.edn` [accordingly](https://github.com/seancorfield/clj-new#getting-started).

Then, to create a new Vrac project:

```shell script
clj -A:new vrac group-id/project-name
```

You may use your name as a `group-id` in the command above.

## Notes for template developers

- Generating the template without deployment or jar building:
  ```shell script
  clj -m clj-new.create vrac group-id/project-name
  ```
- Building the jar file:
  ```shell script
  clojure -A:jar
  ```
- Generating the template using the jar file:
  ```shell script
  clojure -Sdeps '{:deps {vrac/clj-template {:local/root "/path/to/clj-template.jar"}}}' -A:new vrac vincent/test-case
  ```
- Uploading the jar to Clojars:
  ```shell script
  mvn deploy:deploy-file -Dfile=clj-template.jar -DpomFile=pom.xml -DrepositoryId=clojars -Durl=https://clojars.org/repo/
  ```

## Thanks

Big thank to Sean Corfield for implementing [clj-new](https://github.com/seancorfield/clj-new).

## License

Copyright © 2020 Vincent Cantin

Distributed under the Eclipse Public License either version 2.0 or (at your option) any later version.
