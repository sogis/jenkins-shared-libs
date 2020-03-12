def call(String appName, String repo, String stage) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            def dcConfig = openshift.process( "${repo}/deploymentconfig.yaml", "-p", "NAMESPACE=${stage}" )
            openshift.apply( dcConfig )
            dc = openshift.selector( "dc/${appName}" )
            // dc.rollout().status()
            openshiftDeploy(depCfg: 'qwc-service')
            }
        }  
    }
