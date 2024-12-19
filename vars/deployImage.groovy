def call(String appName, String stage, String repo, String environment) {
    sh """
        wget --header='Authorization: token ${PwdGitUser}' $repo/deploymentconfig.yaml
        oc process -f deploymentconfig.yaml -p \$(curl -H 'Authorization: token ${PwdGitUser}' -s ${repo}/${appName}_deployment_${environment}.params) | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
    """
    }
