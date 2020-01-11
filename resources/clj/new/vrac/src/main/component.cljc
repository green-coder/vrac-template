(ns {{namespace}}.component
  (:require [{{namespace}}.data :as data]
            #?(:clj [vrac.core :as v :refer [defc]]
               :cljs [vrac.core :as v :refer-macros [defc]])))

(defc user-comp [user]
  {:id :app/user}
  (let [name (:github.user/name user)
        blog (:github.user/blog user)]
    [:div "Made by: " name
     (when blog
       [:span " (" [:a {:href blog} blog] ")"])]))

(defc root-comp []
  {:id :app/root}
  (let [project (::data/vrac-project nil)
        name (:github.project/name project)
        language (:github.project/language project)
        owner (:github.project/owner project)]
    [:div
     [:div "Project: " name]
     [:app/user {:user owner}]
     [:div "With love, using: " language]]))

(def all-components [root-comp user-comp])

(comment
  (let [env {:components (into {} (map (juxt :id identity)) all-components)}]
    (v/get-queries env (:id root-comp))))
