(ns wallman.ncore
  (:use seesaw.core)
  (:gen-class)
  (:import hu.kazocsaba.imageviewer.ImageViewer
           java.io.File
           clojure.lang.ISeq
           javax.imageio.ImageIO))

(defn ^ISeq get-wallpapers [^File dir]
  "Returns a sequence of all wallpapers in a dir"
  (-> dir
      .listFiles
      seq))

(defn ^javax.swing.JComponent image-viewer [^File file]
  "Returns an image viewer component"
  (-> file
      ImageIO/read
      ImageViewer.
      .getComponent))

(defn wallpaper-chooser [^File dir & {:keys [handler]}]
  "Returns a combo box containing some wallpapers"
  (let [combo-box (combobox :model (get-wallpapers dir))]
    (listen combo-box :selection handler)
    combo-box))

(defn -main [& args]
  "Runs the image viewer.
  ---------------------------
  |     |------------|      |
  |                         |
  |                         |
  |                         |
  |                         |
  |                         |
  |                         |
  |                         |
  |                         |
  ---------------------------
  something like ^"
  (let [wallpapers (get-wallpapers (File. (first args)))
        cbox (combobox :model wallpapers :renderer #(config! %1 :text (.getName (:value %2))))
        applyb (button :text "Apply")
        vl (vertical-panel :items [cbox "Nada."])
        f (frame :title "Wallpaper Manager 0.0.2" :content vl)]
    (listen cbox :selection (fn [e] (config! vl :items [cbox (image-viewer (selection e)) applyb])))
    (listen applyb :action (fn [e] (.. Runtime getRuntime (exec (str "/usr/bin/feh --bg-scale " (.getPath (selection cbox)))))))
    (-> f pack! show!)))
