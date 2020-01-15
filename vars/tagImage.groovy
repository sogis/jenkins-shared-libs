def call(String appName, String version) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( openshift.selector( "dc/${appName}" ).exists() ) {
                dc = openshift.selector( "dc/${appName}" ).object()
                dcImage = dc.spec.template.spec.containers[0].image
            }
            else {
                dcImage = ''
            }
            if ( dcImage != newImage ) { 
                openshift.tag( "${appName}:latest", "${appName}:${version}")
            }
            else {
                println "No new tag for ${appName} Image because tag already exists"
            }
        }
    }
}
