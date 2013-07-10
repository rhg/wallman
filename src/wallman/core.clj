(ns wallman.core
  (:gen-class)
  (:use seesaw.core)
  (:import java.io.File))

(def ^{:private true :doc "The user's home folder"} home (System/getProperty "user.home"))

(defn- wallpapers [dir]
  "Returns a seq of all wallpapers"
  (when (instance? File dir)
    (let [files (.listFiles dir)]
      (zipmap (map #(.getName %) files) files))))

(defn make-root-frame [dir]
  (let [wallpapers (wallpapers dir)
        lbox (listbox :model (keys wallpapers))
        preview (label)
        layout (border-panel :center lbox :east preview)
        f (frame :content layout :title "Wallpaper Manager" :on-close :exit)]
    (listen lbox :selection #(config! preview :icon (wallpapers (selection %))))
    f))

(defn- make-file [x & rst]
  (loop [a (File. x) b rst]
    (if (nil? (first b))
      a
      (recur (File. a (first b)) (rest b)))))

(defn -main [& args]
  (-> (make-root-frame (make-file (first args)))
      pack!
      show!))
