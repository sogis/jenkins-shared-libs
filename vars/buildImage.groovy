def call(String appName, String stage, String repo, String environment, String arguments) {
    sh """
       oc process -f ${repo}/buildconfig.yaml --param-file={$repo}/${appName}_build_${environment}.params | oc apply -n $stage -f- 
       oc start-build $appName -n $stage --from-repo=qwc_services --wait $arguments
    """
    }
