(ns bhlieberman.s3.client
  (:require [cognitect.aws.client.api :as aws]
            [cognitect.aws.credentials :refer [basic-credentials-provider]])
  (:import [javax.imageio ImageIO]))

(gen-class
 :name bhlieberman.s3.client.Client
 :main false
 :prefix "-"
 :methods [^{:static true} [getImage [String String] java.awt.image.BufferedImage]])

(defn -getImage [bucket key]
  (let [s3 (aws/client {:api :s3
                        :region "us-west-2"
                        :credentials-provider
                        (basic-credentials-provider {:access-key-id (System/getenv "AWS_ACCESS_KEY_ID")
                                                     :secret-access-key (System/getenv "AWS_SECRET_KEY")})})
        opts {:op :GetObject
              :request {:Bucket bucket
                        :Key key}}]
    (-> s3 (aws/invoke opts) :Body ImageIO/read)))