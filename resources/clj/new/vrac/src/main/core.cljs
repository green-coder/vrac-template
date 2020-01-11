(ns {{namespace}}.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [vrac.core :as v]
            [vrac.react.core :as vr]
            [{{namespace}}.data :as data]
            [{{namespace}}.component :refer [all-components]]))

; Compose an global environment using the Vrac components and each system.
(def env
  (-> {:pathom-env data/env}
      (v/with-components all-components)
      (vr/with-react-renderer)))

; Loads required data from server and render the root component.
(defn render [component-id]
  (go
    (let [root-query (v/get-queries env component-id)
          data (<! (data/query-parser (:pathom-env env) root-query))]
      (vr/render env component-id {nil data} "app"))))

; Invoked by shadow-cljs
(defn ^:export init! []
  (println "(init!)")
  (render :app/root))

; Invoked by shadow-cljs
(defn ^:export reload! []
  (println "(reload!)")
  (render :app/root))
