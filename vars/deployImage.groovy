def call(String appName, String stage, String version) {
    sh """
        oc process -f https://raw.githubusercontent.com/sogis/pipelines/master/api_webgisclient/data-service/deploymentconfig.yaml -p NAMESPACE=$stage -p TAG=$version | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
    """
    }
