def call(String appName, String repo, String stage, String version, String parameters) {
    sh """
       oc process ${repo}/deploymentconfig.yaml $parameters | oc apply -n $stage -f-
       oc patch dc $appName -p '{"spec":{"template":{"spec":{"containers":[{"name":"sogis-$appName","image":"docker-registry.default.svc:5000/$stage/$appName:$version"}]}}}}'
       oc rollout status
    """
   // openshift.withCluster() {
   //     openshift.withProject('gdi-devel') {
   //         def dcConfig = openshift.process( "${repo}/deploymentconfig.yaml", "-p", "NAMESPACE=${stage}" )
   //         for ( o in dcConfig ) {
   //             o.spec.template.spec.containers[0].image = "docker-registry.default.svc:5000/gdi-devel/qwc-service:${version}"
   //             }
   //         openshift.apply( dcConfig )
   //         dc = openshift.selector( "dc/${appName}" )
   //         dc.rollout().status()
   //         }
   //     }  
    }
