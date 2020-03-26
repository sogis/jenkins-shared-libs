def call(String appName, String repo, String stage, String version, List dcConfig, String parameters) {
    println dcConfig
    for  ( o in dcConfig ) {
        o.spec.template.spec.containers[0].image = "docker-registry.default.svc:5000/gdi-devel/qwc-service:${version}"
        }
    openshift.apply( dcConfig )
    dc = openshift.selector( "dc/${appName}" )
    dc.rollout().status()
    //sh """
    //   oc process -f ${repo}/deploymentconfig.yaml $parameters | oc apply -n $stage -f-
    // """
//       oc patch dc $appName -n ${stage} -p '{"spec":{"template":{"spec":{"containers":[{"name":"sogis-$appName","image":"docker-registry.default.svc:5000/$stage/$appName:$version"}]}}}}'
    //   oc rollout -n $stage status dc $appName 
    }
