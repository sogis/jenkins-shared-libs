def call(List credentials, String args, String appName){
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( !openshift.selector("bc" , appName).exists() ) {
                withCredentials(credentials) {
                    openshift.verbose()
                
                    def bc = openshift.newBuild( "https://${gitlabUser}:${gitlabPwd}@git.sourcepole.ch/ktso/somap.git", "${args}")
                    def builds = bc.related('builds')
                    // Wait until all builds finished
                    builds.untilEach(1) {
                        return it.object().status.phase == "Complete"
                    }
                    openshift.verbose(false)
                }
            }
        }
    }
}
