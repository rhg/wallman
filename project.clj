(defproject wallman "0.2.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot [wallman.core]
  :main wallman.core
  :dependencies [[seesaw "1.4.3"]
                 [hu.kazocsaba/image-viewer "1.2.3"]
                 [org.clojure/clojure "1.4.0"]])
