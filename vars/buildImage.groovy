def call(String appName, String repo, String stage, String params, String arguments) {
    sh """
       oc process -f $repo/buildconfig.yaml $params | oc apply -n $stage -f- 
       oc start-build $appName -n $stage --wait $arguments
    """
    }
