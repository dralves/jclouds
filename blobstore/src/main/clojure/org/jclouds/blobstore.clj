(ns org.jclouds.blobstore
  "A clojure binding for the jclouds BlobStore.

Current supported services are:
   [s3, azureblob, atmos, cloudfiles]

Here's a quick example of how to view blob resources in rackspace

    (use 'org.jclouds.blobstore)
    (use 'clojure.contrib.pprint)

    (def user \"rackspace_username\")
    (def password \"rackspace_password\")
    (def blobstore-name \"cloudfiles\")

    (with-blobstore [blobstore-name user password]
      (pprint (containers))
      (pprint (blobs blobstore your_container_name)))

See http://code.google.com/p/jclouds for details."
  (:use [org.jclouds.core]
        [clojure.contrib.duck-streams :only [copy]])
  (:import [java.io File FileOutputStream OutputStream]
           [org.jclouds.blobstore
            AsyncBlobStore BlobStore BlobStoreContext BlobStoreContextFactory
            domain.BlobMetadata domain.StorageMetadata domain.Blob
            options.ListContainerOptions]
           [com.google.common.collect ImmutableSet]))

(defn blobstore
  "Create a logged in context.
Options for communication style
     :sync and :async.
Options can also be specified for extension modules
     :log4j :enterprise :httpnio :apachehc :bouncycastle :joda :gae
"
  [#^String service #^String account #^String key & options]
  (let [context
        (.createContext
         (BlobStoreContextFactory.) service account key
         (apply modules (filter #(not #{:sync :async} %) options)))]
    (if (some #(= :async %) options)
      (.getAsyncBlobStore context)
      (.getBlobStore context))))

(defn blobstore-context
  "Returns a blobstore context from a blobstore."
  [blobstore]
  (.getContext blobstore))

(defn blobstore?
  [object]
  (or (instance? BlobStore object)
      (instance? AsyncBlobStore object)))

(defn blobstore-context?
  [object]
  (instance? BlobStoreContext object))

(defn as-blobstore
  "Tries hard to produce a blobstore from its input arguments"
  [& args]
  (cond
   (blobstore? (first args)) (first args)
   (blobstore-context? (first args)) (.getBlobStore (first args))
   :else (apply blobstore args)))

(def *blobstore*)

(defmacro with-blobstore [[& blobstore-or-args] & body]
  `(binding [*blobstore* (as-blobstore ~@blobstore-or-args)]
     ~@body))

(defn- parse-args
  "Parses arguments, recognises keywords in the set single as boolean switches."
  [args single default]
  (loop [[arg :as args] args
         opts default]
    (if-not args
      opts
      (if (single arg)
        (recur (next args) (assoc opts arg true))
        (recur (nnext args) (assoc opts arg (second args)))))))

(def list-options
     (apply array-map
            (concat (make-option-map option-fn-0arg [:recursive])
                    (make-option-map option-fn-1arg [:after-marker :in-directory :max-results]))))

(defn- list-options-apply
  [single target key value]
  (if (single key)
    ((list-options key) target)
    ((list-options key) target value))
  target)

(defn containers
  "List all containers in a blobstore."
  ([] (containers *blobstore*))
  ([blobstore] (.list blobstore)))

(defn list-container
  "List a container. Options are:
  :after-marker string
  :in-direcory path
  :max-results n
  :recursive"
  [blobstore & args]
  (if (blobstore? blobstore)
    (let [single-keywords #{:recursive}
          options (parse-args (next args) single-keywords {})
          list-options (reduce
                        #(list-options-apply single-keywords %1 (first %2) (second %2))
                        (ListContainerOptions.)
                        options)]
      (.list blobstore (first args) list-options))
    (apply list-container *blobstore* blobstore args)))

(defn create-container
  "Create a container."
  ([container-name]
     (create-container *blobstore* "default" container-name))
  ([blobstore container-name]
     (if (blobstore? blobstore)
       (create-container blobstore "default" container-name)
       (create-container *blobstore* container-name blobstore)))
  ([blobstore container-name location-name]
     (.createContainerInLocation blobstore container-name location-name)))

(defn clear-container
  "Clear a container."
  ([container-name]
     (clear-container container-name))
  ([blobstore container-name]
     (.clearContainer blobstore container-name)))

(defn delete-container
  "Delete a container."
  ([container-name]
     (delete-container *blobstore* container-name))
  ([blobstore container-name]
     (.deleteContainer blobstore container-name)))

(defn container-exists?
  "Predicate to check presence of a container"
  ([container-name]
     (container-exists? *blobstore* container-name))
  ([blobstore container-name]
     (.containerExists blobstore container-name)))

(defn directory-exists?
  "Predicate to check presence of a directory"
  ([container-name path]
     (directory-exists? *blobstore* container-name path))
  ([blobstore container-name path]
     (.directoryExists blobstore container-name path)))

(defn create-directory
  "Create a directory path."
  ([container-name path]
     (create-directory *blobstore* container-name path))
  ([blobstore container-name path]
     (.createDirectory blobstore container-name path)))

(defn delete-directory
  "Delete a directory path."
  ([container-name path]
     (delete-directory *blobstore* container-name path))
  ([blobstore container-name path]
     (.deleteDirectory blobstore container-name path)))

(defn blob-exists?
  "Predicate to check presence of a blob"
  ([container-name path]
     (blob-exists? *blobstore* container-name path))
  ([blobstore container-name path]
     (.blobExists blobstore container-name path)))

(defn put-blob
  "Put a blob.  Metadata in the blob determines location."
  ([container-name blob]
     (put-blob *blobstore* container-name blob))
  ([blobstore container-name blob]
     (.putBlob blobstore container-name blob)))

(defn blob-metadata
  "Get blob metadata from given path"
  ([container-name path]
     (blob-metadata *blobstore* container-name path))
  ([blobstore container-name path]
     (.blobMetadata blobstore container-name path)))

(defn get-blob
  "Get blob from given path"
  ([container-name path]
     (get-blob *blobstore* container-name path))
  ([blobstore container-name path]
     (.getBlob blobstore container-name path)))

(defn remove-blob
  "Remove blob from given path"
  ([container-name path]
     (remove-blob *blobstore* container-name path))
  ([blobstore container-name path]
     (.removeBlob blobstore container-name path)))

(defn count-blobs
  "Count blobs"
  ([container-name]
     (count-blobs *blobstore* container-name))
  ([blobstore container-name]
     (.countBlob blobstore container-name)))

(defn blobs
  "List the blobs in a container:
blobstore container -> blobs

list the blobs in a container under a path:
blobstore container dir -> blobs

example:
    (pprint
      (blobs
      (blobstore-context flightcaster-creds)
       \"somecontainer\" \"some-dir\"))
"
  ([blobstore container-name]
     (.list (.getBlobStore blobstore) container-name ))

  ([blobstore container-name dir]
     (.list (.getBlobStore blobstore) container-name
            (.inDirectory (new ListContainerOptions) dir))))

(defn create-blob
  "Create an blob representing text data:
container, name, string -> etag
"
  ([container-name name data]
     (create-blob *blobstore* container-name name data))
  ([blobstore container-name name data]
     (put-blob blobstore container-name
               (doto (.newBlob blobstore name)
                 (.setPayload data)))))

(defmulti #^{:arglists '[[container-name name target]
                         [blobstore container-name name target]]}
  download-blob (fn [& args]
                  (if (= (count args) 3)
                    ::short-form
                    (class (last args)))))

(defmethod download-blob ::short-form
  [container-name name target]
  (download-blob *blobstore* container-name name target))

(defmethod download-blob OutputStream [blobstore container-name name target]
  (let [blob (get-blob blobstore container-name name)]
    (copy (.getContent blob) target)))

(defmethod download-blob File [blobstore container-name name target]
  (download-blob blobstore container-name name (FileOutputStream. target)))

(define-accessors StorageMetadata "blob" type id name
  location-id uri last-modfied)
(define-accessors BlobMetadata "blob" content-type)

(defn blob-etag [blob]
  (.getETag blob))

(defn blob-md5 [blob]
  (.getContentMD5 blob))
