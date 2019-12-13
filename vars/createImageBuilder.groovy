def call(List credentials, String args, String serviceName){
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( !openshift.selector("bc" , serviceName).exists() )
                withCredentials(credentials) {
                openshift.verbose()
                openshift.newBuild( "https://${gitlabUser}:${gitlabPwd}@git.sourcepole.ch/ktso/somap.git", "${args}")
                openshift.verbose(false)
            }
        }
    }
}
