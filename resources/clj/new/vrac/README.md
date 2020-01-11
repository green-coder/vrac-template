# {{name}}

A [Vrac](https://github.com/green-coder/vrac) project from the future.

## Usage

- [Install shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html#_installation) if it is not already on your system.
- Load your NPM modules (you only need to do this once):
  ```shell script
  npm install
  ```
- Compile your CLJS program:
    ```shell script
    shadow-cljs watch main
    ```
- Wait until the compilation completes.

  The initial compilation might take some time, but it gets a lot faster
  when you subsequently update your source code.
  You can monitor the compilation process at http://localhost:9630/
- Open your browser and see your website at http://localhost:8000/
- When you modify the source code, shadow-cljs will hot reload your website in the browser automatically.

## Connect a REPL to the browser

- Connect your REPL client to localhost:9000

  You are now connected to the running instance of shadow-cljs.
- In the REPL, type:
  ```clojure
  (shadow/repl :main)
  ```
- Your CLJ REPL session should become a CLJS session and you should be connected to the browser.
  You can verify if that worked by typing:
  ```clojure
  (js/alert "Hello, browser!")
  ```
  Take a look at your browser, you should see an alert window with the message.

In case of problems during your setup, please consult
[Shadow-CLJS's Users Guide](https://shadow-cljs.github.io/docs/UsersGuide.html).

## Resources

- The [Vrac](https://github.com/green-coder/vrac) library
- The #vrac channel on [Slack](https://clojurians.slack.com)
