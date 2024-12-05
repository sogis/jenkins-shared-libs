def call(String appName, String stage, String repo, String environment) {
    sh """
        oc process -f $repo/deploymentconfig.yaml -p \$(curl -s ${repo}/${appName}_${environment}.params) | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
    """
    }
