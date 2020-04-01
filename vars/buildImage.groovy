def call(String appName, String repo, String stage, String arguments) {
    sh """
       oc process -n $stage -f $repo/buildconfig.yaml | oc apply -f- 
       oc start-build $appName -n $stage --wait $arguments
    """
    }
