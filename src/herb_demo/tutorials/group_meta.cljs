(ns herb-demo.tutorials.group-meta
  (:require-macros [herb-demo.macros :as macros])
  (:require [garden.units :refer [em px rem]]
            [herb-demo.components.code :refer [code]]
            [herb-demo.components.paper :refer [paper]]
            [herb-demo.components.text :refer [text]]
            [herb-demo.snippets.group :as group]
            [herb-demo.snippets.my-group :as my-group]
            [herb-demo.snippets.my-group-args :as my-group-args]
            [herb-demo.snippets.group-pattern :as group-pattern]
            [herb.core :as herb :refer-macros [<class <id]]
            [reagent.core :as r]
            [reagent.debug :as d]))

(defn group-meta
  []
  (let [e1 (macros/example-src "group.cljs")
        e2 (macros/example-src "group.html")
        e3 (macros/example-src "group_pattern.cljs")
        e4 (macros/example-src "group_pattern.html")
        e5 (macros/example-src "my_group.cljs")
        e6 (macros/example-src "my_group_args.cljs")]
    [paper
     [text {:variant :title}
      "Group metadata"]
     [text
      "Another kind of meta data that works in conjuction with `:key` is the
     `:group` meta. With this flag we can group styles that share a function, so
     as to create a bit less noise in the `<head>`. Lets use the example from
     the previous section:"]
     [code {:lang :clojure}
      e1]
     [text
      "In the DOM we now see a single style element:"]
     [code {:lang :html}
      e2]
     [text
      "This meta data can be useful when grouping static styles, observe this
      pattern:"]
     [code {:lang :clojure}
      e3]
     [text {:variant :subheading}
      "Output"]
     [group-pattern/component]
     [text "Here is what the DOM looks like:"]
     [code {:lang :html}
      e4]
     [text
      "This pattern is a bit verbose, and I included it to demonstrate how the
    group meta works. There is a macro `defgroup` in `herb.core` that simplify
    creating style groups:"]
     [code {:lang :clojure}
      e5]
     [text
      "This example and the previous one are exactly the same, just wrapped in a
     sugary macro. Its also possible to pass arguments to a group, the macro
     stores the all but the first arguments in a variable calls `args`:"]
     [code {:lang :clojure}
      e6]
     [text
      "I would advice against passing args to a stylegroup though, because if you're
      updating any single run of a stylegroup function the entire group has to
      be re-rendered"]]))
