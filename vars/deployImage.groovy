def call(String appName, String repo, String stage, String version, String parameters) {
    sh """
       oc process -f ${repo}/deploymentconfig.yaml $parameters | oc apply -n $stage -f-
    """
//       oc patch dc $appName -n ${stage} -p '{"spec":{"template":{"spec":{"containers":[{"name":"sogis-$appName","image":"docker-registry.default.svc:5000/$stage/$appName:$version"}]}}}}'
    //   oc rollout -n $stage status dc $appName 
    }
