def call(String appName, String stage, String version, List dcConfig) {
    openshift.withCluster() {
        openshift.withProject(stage) {
            for  ( o in dcConfig ) {
                o.spec.template.spec.containers[0].image = "docker-registry.default.svc:5000/gdi-devel/qwc-service:${version}"
                }
            openshift.apply( dcConfig )
            dc = openshift.selector( "dc/${appName}" )
            dc.rollout().status()
            }
        }
    }
