def call(String appName, String newImage, String repo) {
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
                println dcImage
                println newImage
                def buildConfig = openshift.process( "${repo}/buildconfig.yaml" )
                openshift.apply( buildConfig )
                def builds = openshift.selector( "bc", appName).startBuild( "--wait", "--build-arg QWC2_VERSION=${newImage}", "--build-arg GIT_VERSION=${newImage}" )
            }
            else {
                println "No build for ${appName} because Image already used"
            }
        }
    }
}
