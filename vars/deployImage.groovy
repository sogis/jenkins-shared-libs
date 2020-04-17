def call(String appName, String stage, String version, List dcConfig) {
    openshift.withCluster() {
        openshift.withProject(stage) {
            for  ( o in dcConfig ) {
                o.spec.template.spec.containers[0].image = "docker-registry.default.svc:5000/${stage}/${appName}:${version}"
                }
            sh """
                oc process -f https://raw.githubusercontent.com/sogis/pipelines/master/api_webgisclient/data-service/deploymentconfig.yaml -p NAMESPACE=${stage} -p TAG=2.0.16 | oc apply -n $stage -f-
                oc rollout latest -n $stage $app
                oc rollout status -n $stage dc $app
            """
            //dc = openshift.selector( "dc/${appName}" )
            //dc.rollout().latest()
            //dc.rollout().status()
            }
        }
    }
