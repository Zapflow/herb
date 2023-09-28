(ns herbdemo.examples
  (:require
    [herb.core :refer [<class defgroup join defkeyframes defglobal]]
    ; [garden.selectors :as s]
    ; [garden.core :refer [css]]
    ; [garden.stylesheet :refer [at-media]]
    [garden.units :refer [px em rem]]
    ; [reagent.debug :as d]
    [reagent.core :as r])
  )

(def red {:color "red"})
(def green {:color "green"})

(defglobal global
           [:.global {:color     "magenta"
                      :font-size (px 24)}])

(defn state-hover
  [color]
  ^{:pseudo {:hover {:color "yellow"}}}
  {:margin-bottom    0
   :background-color color})

(defn toggle-color
  [previous]
  (case previous
    "red" "green"
    "green" "red"))

(defn inheritance-meta
  []
  ^{:pseudo {:hover {:background-color "black"}}}
  {:color "red"})

(defn extend-mode-meta
  []
  ^{:extend inheritance-meta
    :pseudo {:hover {:background-color "green"}}}
  {:color "purple"})

(defn hover-focus
  []
  ^{:extend extend-mode-meta
    :pseudo {:hover {:background-color "blue"}
             :focus {:background-color "yellow"}}}
  {:background-color "red"}
  )

(defn font-weight
  [weight]
  {:font-weight weight})

(defn dynamic-text-color
  [color]
  (with-meta
    (case color
      (or "blue" "purple") {:color "white"}
      {:color "black"})
    {:extend [[font-weight "bold"] [inheritance-meta]]}))

(defn margin
  []
  {:margin "5px"})

(defn italic
  []
  ^{:pseudo {:hover {:font-style "normal"}}}
  {:font-style "italic"})

(defn bold
  []
  ^{:extend [[italic] [margin]]}
  {:font-weight "bold"})

(defn cycle-color
  [color]
  ^{:extend [[dynamic-text-color color] [bold]]}
  {:text-align       "center"
   :background-color color}
  )

(defn button
  []
  {:border "2px solid red"
   :margin (px 10)})

(defn blue-div
  []
  {:color            "black"
   :background-color "blue"})

(defn red-div
  []
  ^{:extend blue-div
    :media  {{:screen true} {:color "magenta"}}}
  {:color            "white"
   :background-color "red"})

(defn cyan-div
  []
  ^{:extend red-div
    :media  {{:screen true} {:color            "white"
                             :background-color "purple"}}}
  {:background-color "cyan"})

(defn media-query-test
  [color]
  ^{:extend cyan-div
    :media  {{:screen true}       {:color            "white"
                                   :background-color color}
             {:max-width "800px"} {:background-color "yellow"}}}
  {:background "magenta"
   :text-align "center"
   :color      "white"})

(defn profile-comp
  [_]
  {:width            (px 100)
   :height           (px 100)
   :background-color "magenta"
   :border-radius    "5px"})

(defn extend-1
  [color]
  {:background-color color})

(defn extend-2
  [color]
  ^{:extend [extend-1 "green"]}
  {:color color})

(defn extend-3
  []
  ^{:extend [extend-2 "red"]}
  {:color "yellow"})

(defn style-group-stateful
  [component color]
  (component
    {:text   {:color color}
     :box    {:background-color color
              :padding          (px 12)}
     :text-2 {:color color}}))

(defn style-group-static
  [component]
  (component
    {:text   {:color "magenta"}
     :box    {:background "white"
              :padding    (px 12)}
     :text-2 {:color "cyan"}}))

(defgroup group-macro
          {:text {:font-weight "bold"
                  :color       "white"}
           :box  {:background-color "#333"
                  :padding          (px 5)
                  :margin           [["10px" 0 "10px" 0]]
                  :border-radius    "5px"}})

(defgroup group-with-args
          {:text {:font-style "italic"
                  :color      "white"}
           :box  {:background (first args)}})

(defn supports-thing
  []
  ^{:supports {{:display :grid} {:font-size (px 20)}}}
  {:font-size (px 12)}
  )

(defn container
  []
  ^{:media {{:screen :only :min-width (em 32)} {:width (rem 33)}
            {:screen :only :min-width (em 52)} {:width (rem 53)}}}
  {:margin "0 auto"})

(defn extend-group
  []
  ^{:extend [[group-macro :box] [group-macro :text]]}
  {:background-color "red"})

(defn white-text
  []
  {:color "white"})

(defn inline-prefixes
  []
  {:-moz             {:border-radius "3px"
                      :box-sizing    "border-box"}
   :background-color "black"
   :color            "white"})

(defn shadow-prefix
  []
  {:transition "all 1s ease-out"
   :font-size  (px 24)})

(defn test-global-prefixes
  []
  ^{:extend shadow-prefix}
  {:background-color "black"
   :border-radius    (px 3)
   :color            "white"})

(defgroup multiple-vendor-group
          {:component-1
           ^{:vendors ["webkit"]
             :prefix  true}
           {:transition "all 1s ease-in"}
           :component-2
           ^{:vendors ["ms"]
             :prefix  true}
           {:border-radius "50%"}})

(defgroup multiple-meta-group
          {:component-1
           ^{:pseudo {:hover {:border-radius "50%"}}}
           {:width      (px 60)
            :transition "all 0.5s ease-in"
            :height     (px 60)
            :background "black"}})

(defn transition
  [_]
  {:transition "all 1s ease-out"})

(declare pulse-animation)
(defkeyframes pulse-animation
              [:from {:opacity 1}]
              [:to {:opacity 0}])

(declare width-vary)
(defkeyframes width-vary
              [:from {:width "100%"}]
              [:to {:width "50%"}])

(declare percentages)
(defkeyframes percentages
              ["0%" {:top 0 :left 0}]
              ["30%" {:top (px 50)}]
              ["68%" {:left (px 50)}])

(defn pulse-component
  []
  {
   :animation        [[pulse-animation "2s" :infinite :alternate]]
   :background-color "black"
   :width            (px 20)
   :height           (px 20)}
  )

(defn pulse-component-two
  []
  {:animation        [[pulse-animation "2s" :infinite :alternate]]
   :background-color "green"
   :width            (px 20)
   :height           (px 20)})

(defn width-vary-component
  []
  {:animation        [[width-vary "2s" "cubic-bezier(.77, 0, .175, 1)" :infinite :alternate]]
   :background-color "red"
   :height           (px 20)})

(defn row
  []
  {:display :flex})

(defn simple
  []
  {:background-color "red"})

(def data [{:color :red} {:color :green}])

(defn data-str
  [in]
  {:background-color (:color in)})

(defn large
  [_]
  {:background "magenta"
   :width      "100%"
   :height     "20px"
   :margin     [["10px" 0 "10px" 0]]})

(defn simple-state [color]
  {:background color
   :height     (px 100)
   :width      "100%"})

(defn selector-inherit
  []
  ^{:combinators {[:- :div] {:background "yellow"}}}
  {:font-size "16px"})

(defn selector-test []
  ^{:extend      selector-inherit
    :combinators {[:> :div :span]    {:margin-left "10px"
                                      :background  "red"}
                  [:+ :p]            {:background  "purple"
                                      :margin      0
                                      :margin-left "20px"}
                  [:descendant :div] {:background "green"}}}
  {:background :blue
   :color      :white})

; Selectors
(defn attr-selector
  []
  ^{:attributes {[:attr :data-section-title]            {:font-size "25px"}
                 [:attr= :data-value :bg-pink]          {:background "pink"}
                 [:attr-contains :data-value :test]     {:background "lightblue"}
                 [:attr-matches :data-value :test]      {:background "green"}
                 [:attr-starts-with :data-value :test]  {:background "lightgrey"}
                 [:attr-starts-with* :data-value :test] {:background "pink"}
                 [:attr-ends-with :data-value :test]    {:background "orange"}}}
  {:font-size    "20px"
   :padding-left "20px"})

(defn main []
  (let [state (r/atom "green")]
    (fn []

      #_[:div
         [:div {:class (<class simple-state "green")}]
         [:div {:class (<class simple-state "green")}]
         [:div {:class (<class simple-state "red")}]
         [:div {:class (<class simple-state "red")}]]

      #_[:div
         [:div {:class (<class simple-state @state)}]
         [:br]
         [:button {:on-click #(swap! state toggle-color)}
          "Change color"]
         ]

      [:div
       {:class (<class container)}

       [:div
        {:data-section-title ""
         :class              (<class attr-selector)}
        "Attribute selectors"]


       [:div
        {:data-value :bg-pink
         :class      (<class attr-selector)}
        "[attr]"]

       [:div
        {:data-value :test
         :class      (<class attr-selector)}
        "[attr=value]"]


       [:div
        {:data-value "value test value"
         :class      (<class attr-selector)}
        "[attr~=value]"]

       [:div
        {:data-value "value-test-value"
         :class      (<class attr-selector)}
        "[attr*=value]"]

       [:div
        {:data-value "testvalue"
         :class      (<class attr-selector)}
        "[attr^=value]"]

       [:div
        {:data-value "test-value"
         :class      (<class attr-selector)}
        "[attr|=value]"]

       [:div
        {:data-value "valuetest"
         :class      (<class attr-selector)}
        "[attr$=value]"]

       ]

      [:div {:class (<class container)}
       [:div
        {:data-section-title ""
         :class              (<class attr-selector)}
        "Attribute selectors"]


       [:div
        {:data-value :bg-pink
         :class      (<class attr-selector)}
        "[attr]"]

       [:div
        {:data-value :test
         :class      (<class attr-selector)}
        "[attr=value]"]


       [:div
        {:data-value "value test value"
         :class      (<class attr-selector)}
        "[attr~=value]"]

       [:div
        {:data-value "value-test-value"
         :class      (<class attr-selector)}
        "[attr*=value]"]

       [:div
        {:data-value "testvalue"
         :class      (<class attr-selector)}
        "[attr^=value]"]

       [:div
        {:data-value "test-value"
         :class      (<class attr-selector)}
        "[attr|=value]"]

       [:div
        {:data-value "valuetest"
         :class      (<class attr-selector)}
        "[attr$=value]"]
       (for [d data]
         ^{:key d}
         [:div {:class (<class data-str d)}
          (:color d)])
       [:div {:class (<class group-with-args :box "black")}
        [:span {:class (<class group-with-args :text)}
         "Group that takes args"]]
       [:div
        [:div {:class (<class selector-test)}
         "Testing selectors"
         [:div
          [:span "Child combinator"]]
         [:div
          [:span
           "Child combinator"]]]
        [:p "Adjacent sibling"]
        [:div "General sibling"]]
       [:div.global "global style"]
       [:div {:class (join (<class row) (<class simple))}
        "multiple classes"]
       [:div {:class (<class multiple-meta-group :component-1)}]
       [:div {:class (<class multiple-vendor-group :component-1)}
        "Component-1"]
       [:div {:class (<class multiple-vendor-group :component-2)}
        "Component-2"]
       [:div {:class (<class row)}
        [:div {:class (<class pulse-component)}]
        [:div {:class (<class pulse-component-two)}]
        [:div {:class (<class width-vary-component)}]]
       [:div {:class (<class transition @state)}
        [:button {:on-click #(swap! state toggle-color)}
         "Change color"]]
       [:div {:class (<class inline-prefixes)}
        "inline vendor prefixes"]
       [:div {:class (<class supports-thing)}
        "Testing supports queries"]
       [:div {:class (<class extend-group)}
        "Extend a style group"]
       [:div {:class [(<class group-macro :box) (<class white-text) "hello" nil]}
        "Multiple classes"]

       [:div {:class (<class style-group-stateful :box "#ddd")}
        [:span {:class (<class style-group-stateful :text @state)}
         "group meta test with arguments"]
        [:br]
        [:span {:class (<class style-group-stateful :text-2 "red")}
         "group meta test with arguments"]]

       [:div {:class (<class style-group-static :box)}
        [:span {:class (<class style-group-static :text)}
         "Group meta test without arguments"]
        [:br]
        [:span {:class (<class style-group-static :text-2)}
         "Group meta test without arguments"]]

       [:div {:class (<class group-macro :box)}
        [:span {:class (<class group-macro :text)}
         "Group macro test"]]

       [:div

        [:div {:class (<class extend-3)}
         "Extend test"]

        [:div
         [:div {:class (<class (fn []
                                 ^{:extend button}
                                 {:background-color "black"
                                  :color            "white"}))}
          "Anon function with meta"]
         [:div {:class (<class (fn [color]
                                 {:background-color color
                                  :color            "white"})
                               "black")}
          "Anon function without meta"]]

        [:div {:class (<class (fn [color]
                                {:background "orange"
                                 :color      color})
                              "black")}
         "Same signature anon"]

        (letfn [(nested-fn [color]
                  {:background-color color
                   :color            "white"})]
          [:div {:class (<class nested-fn "black")}
           "letfn block"])

        [:div {:class (<class (fn named-anon []
                                {:background-color "pink"
                                 :color            "black"}))}
         "Named anon fn"
         ]

        [:input {:class         (<class hover-focus)
                 :default-value "test modes hover, active"}]
        [:div
         [:h2 {:class (<class state-hover @state)}
          "Testing hover and state change via button"]
         [:button {:class    (<class button)
                   :on-click #(swap! state toggle-color)}
          "change color"]
         (for [c ["yellow" "blue" "green" "purple"]]
           ^{:key c}
           [:div {:class (<class cycle-color c)}
            c])
         [:br]
         [:div {:class (<class cyan-div)}
          "inheritance test"]]

        [:div {:class (<class media-query-test @state)}
         "Media query test"]]
       [:br]
       [:div {:class (<class simple-state @state)}]
       [:br]
       [:button {:on-click #(swap! state toggle-color)}
        "Change color"]
       [:div
        (for [k (range 100)]
          ^{:key k}
          [:div {:class (<class large k)}])]
       ])))
