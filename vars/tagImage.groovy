def call(String appName, String version) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( dcImage != newImage ) { 
                openshift.tag( "${appName}:latest", "${appName}:${version}")
            }
            else {
                println "No new tag for ${appName} Image because tag already exists"
            }
        }
    }
}
