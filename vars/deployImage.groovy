def call(String appName, String stage, String version, String repo, String params = null) {
    sh """
        oc process -f $repo/deploymentconfig.yaml -p NAMESPACE=$stage -p TAG=$version $params | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
    """
    }
