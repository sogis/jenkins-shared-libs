def call(String baseImage, String appName, String vBaseImage, String vDeployImage, String namespace) {
    openshift.withCluster() {
        openshift.withProject('${namespace}') {
            if ( openshift.selector( "dc/${appName}" ).exists() ) {
                dc = openshift.selector( "dc/${appName}" ).object()
                dcImage = dc.spec.template.spec.containers[0].image
            }
            else {
                dcImage = ''
            }
            if ( dcImage != vDeployImage ) { 
                sh """
                    oc tag -n $namespace --source=docker $baseImage:$vBaseImage $appName:$vDeployImage
                    sleep 2
                    oc tag -n $namespace --source=imagestreamtag $appName:$vDeployImage $appName:latest
                """
            }
            else {
                println "No new tag for ${appName} Image because tag already exists"
            }
        }
    }
}
