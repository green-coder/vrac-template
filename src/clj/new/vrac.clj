(ns clj.new.vrac
  (:require [clj.new.templates :refer [renderer project-data ->files]]))

(defn vrac
  [name]
  (let [render (renderer "vrac")
        data   (project-data name)]
    (println "Importing new Vrac project from the future ...")
    (->files data
             ["README.md" (render "README.md" data)]
             ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]
             ["package.json" (render "package.json" data)]
             ["deps.edn" (render "deps.edn" data)]
             ["resources/public/index.html" (render "resources/public/index.html" data)]
             ["src/main/{{nested-dirs}}/core.cljs" (render "src/main/core.cljs" data)]
             ["src/main/{{nested-dirs}}/component.cljc" (render "src/main/component.cljc" data)]
             ["src/main/{{nested-dirs}}/data.cljc" (render "src/main/data.cljc" data)])
    (println (str "Done! Your project is in the directory " (:name data) "/"))))
