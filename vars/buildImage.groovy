def call(String appName, String newImage) {
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
                def builds = openshift.selector( "bc", appName).startBuild( "--wait" )
            }
            else {
                println "No build for ${appName} because Image already used"
            }
        }
    }
}
