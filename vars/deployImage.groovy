def call(String appName, String stage, String branch, String environment) {
    sh """
        git checkout ${branch}
    """
    if (environment != 'test') {
        sh "sed -i '/\${CPU_LIMIT}/d; /{\\bCPU_LIMIT[A-Za-z0-9_]*\\b/d' api_webgisclient/${appName}/deployment/deployment.yaml"
    }
    sh """
        oc process -f api_webgisclient/${appName}/deployment/deployment.yaml --param-file api_webgisclient/${appName}/deployment/${appName}_deployment_${environment}.params | oc apply -n $stage -f-
        oc rollout latest -n $stage $appName
        oc rollout status -n $stage dc $appName
        git checkout ${branch_webgisclient}
    """
    }
