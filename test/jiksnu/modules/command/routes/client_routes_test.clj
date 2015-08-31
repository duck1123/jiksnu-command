(ns jiksnu.modules.command.routes.client-routes-test
  (:require [ciste.commands :refer [parse-command]]
            [ciste.core :refer [with-context]]
            [clj-factory.core :refer [factory fseq]]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [jiksnu.mock :as mock]
            [jiksnu.test-helper :as th]
            [manifold.deferred :as d]
            [midje.sweet :refer :all]))

(namespace-state-changes
 [(before :facts (th/setup-testing))
  (after :facts (th/stop-testing))])

(future-fact "parse command 'get-page clients'"
  (let [name "get-page"
        args '("clients")]

    (fact "when there are clients"
      (let [client (mock/a-client-exists)]
        (let [ch (d/deferred)
              request {:channel ch
                       :name name
                       :format :json
                       :args args}]
          (let [response (parse-command request)]
            response => map?
            (let [body (:body response)]
              (let [json-obj (json/read-str body :key-fn keyword)]
                json-obj => map?))))))
    ))

