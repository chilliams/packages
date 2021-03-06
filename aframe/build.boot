(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.7.1"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "0.6.1")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom  {:project     'cljsjs/aframe
        :version     +version+
        :description "A JavaScript visualization library for HTML and SVG"
        :url         "https://aframe.io/"
        :scm         {:url "https://github.com/cljsjs/packages"}
        :license     {"MIT" "https://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (str "https://github.com/aframevr/aframe/archive/v" +lib-version+ ".zip")
              :unzip true)
    (sift :move {(re-pattern (str "^.*/aframe-v" +lib-version+ ".js$")) "cljsjs/aframe/development/aframe.inc.js"
                 (re-pattern (str "^.*/aframe-v" +lib-version+ ".min.js$")) "cljsjs/aframe/production/aframe.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.aframe")
    (pom)
    (jar)))
