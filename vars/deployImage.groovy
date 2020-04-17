def call(String appName, String stage, String version, String repo) {
    sh """
        oc process -f $repo/deploymentconfig.yaml -p NAMESPACE=$stage -p TAG=$version | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
    """
    }
