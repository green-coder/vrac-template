(ns {{namespace}}.data
  (:require [com.wsscode.pathom.core :as p]
            [com.wsscode.pathom.connect :as pc :refer [defresolver]]
            #?@(:clj [[clojure.core.async :refer [go <! <!!]]
                      [clojure.data.json :as json]
                      [clj-http.client :as http]]
                :cljs [[cljs.core.async :refer [go <!]]
                       [cljs-http.client :as http]])))

(defn fetch [env path]
  (println (str "loading " path " ..."))
  #?(:clj (-> (http/get path {:accept :json})
              (update :body json/read-json true)
              (go))
     :cljs (http/get path {:with-credentials? false})))


; Example of global resolver
(defresolver vrac-project-resolver [{:keys [host] :as env}
                                    {:keys [] :as params}]
  {::pc/input #{}
   ::pc/output [{::vrac-project [:github.project/full-name]}]}
  {::vrac-project {:github.project/full-name "green-coder/vrac"}})

; Example of resolver that resolves via data fetching from remote servers
(defresolver github-project-resolver [{:keys [host] :as env}
                                      {:keys [github.project/full-name] :as params}]
  {::pc/input #{:github.project/full-name}
   ::pc/output [:github.project/id
                :github.project/name
                :github.project/full-name
                :github.project/language
                {:github.project/owner [:github.user/login
                                        :github.user/avatar-url]}
                :github.project/stargazers-count]}
  (go
    (let [data (:body (<! (fetch env (str host "/repos/" full-name))))]
      {:github.project/id (:id data)
       :github.project/name (:name data)
       :github.project/full-name (:full_name data)
       :github.project/language (:language data)
       :github.project/owner {:github.user/login (-> data :owner :login)
                              :github.user/avatar-url (-> data :owner :avatar_url)}
       :github.project/stargazers-count (:stargazers_count data)})))

(defresolver github-user-resolver [{:keys [host] :as env}
                                   {:keys [github.user/login] :as params}]
  {::pc/input #{:github.user/login}
   ::pc/output [:github.user/login
                :github.user/name
                :github.user/avatar-url
                :github.user/blog
                :github.user/hireable?]}
  (go
    (let [data (:body (<! (fetch env (str host "/users/" login))))]
      {:github.user/login(:login data)
       :github.user/name (:name data)
       :github.user/avatar-url (:avatar_url data)
       :github.user/blog (:blog data)
       :github.user/hireable? (:hireable data)})))

(def all-resolvers
  [vrac-project-resolver
   github-project-resolver
   github-user-resolver])
   ;(pc/alias-resolver :from :to)])

;; The amazing query parser from Pathom
(def query-parser
  (p/parallel-parser
    {::p/env {::p/reader [p/map-reader
                          pc/parallel-reader
                          pc/open-ident-reader
                          p/env-placeholder-reader]
              ::p/placeholder-prefixes #{">"}}
     ::p/mutate pc/mutate-async
     ::p/plugins [(pc/connect-plugin {::pc/register all-resolvers})
                  p/error-handler-plugin
                  p/trace-plugin]}))

(def env {:host "https://api.github.com"})


(comment
  ;; You can evaluate the following expressions while working with a CLJ REPL.
  (<!! (fetch {} "https://api.github.com/repos/green-coder/vrac"))
  (<!! (query-parser env [{::vrac-project [:github.project/id
                                           :github.project/name
                                           :github.project/full-name
                                           :github.project/language
                                           {:github.project/owner [:github.user/login
                                                                   :github.user/name
                                                                   :github.user/blog
                                                                   :github.user/avatar-url]}
                                           :github.project/stargazers-count]}]))

  ;; In a CLJS REPL where there is no <!!, you would use something like:
  (go (prn (<! (fetch {} "https://api.github.com/repos/green-coder/vrac"))))
  (go (prn (<! (query-parser env [{::vrac-project [:github.project/id
                                                   :github.project/name
                                                   :github.project/full-name
                                                   :github.project/language
                                                   {:github.project/owner [:github.user/login
                                                                           :github.user/name
                                                                           :github.user/blog
                                                                           :github.user/avatar-url]}
                                                   :github.project/stargazers-count]}]))))

  _)
