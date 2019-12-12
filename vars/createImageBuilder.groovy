def call(List credentials, String args){
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( !openshift.selector("bc" , "qwc-service").exists() )
                withCredentials(credentials) {
                openshift.verbose()
                openshift.newBuild( "https://${gitlabUser}:${gitlabPwd}@git.sourcepole.ch/ktso/somap.git", "${args}")
                openshift.verbose(false)
            }
        }
    }
}
