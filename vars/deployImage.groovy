def call(String appName, String stage, String branch, String environment) {
    sh """
        git checkout ${branch}
        oc process -f api_webgisclient/${appName}/deployment/deploymentconfig.yaml --param-file api_webgisclient/${appName}/deployment/${appName}_deployment_${environment}.params | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
        git checkout ${branch_webgisclient}
    """
    }
