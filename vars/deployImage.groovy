def call(String appName, String version, String repo, String stage) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            def dc = openshift.process( "${repo}/${appName}.yaml", "-p", "NAMESPACE=${stage}" )
            if ( openshift.selector( "dc/${appName}" ).exists() ) {
                dc = openshift.selector( "dc/${appName}" ).object()
                dcImage = dc.spec.template.spec.containers[0].image
            }
            else {
                dcImage = ''
            }
            if ( dcImage != version ) { 
                for ( o in dc ) {
                    o.spec.template.spec.containers[0].image = "docker-registry.default.svc:5000/gdi-devel/${appName}:${version}"
                }
            }
            openshift.apply( dc )
            else {
                println "No new tag for ${appName} Image because tag already exists"
            }
        }
    }
}
