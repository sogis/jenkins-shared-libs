def call(List credentials, String args){
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( !openshift.selector("bc" , "qwc-service").exists() )
                                   // build qwc-service Image from Sourcepole Source Code
                openshift.newBuild( "https://git.sourcepole.ch/ktso/somap.git", "${args}")
                withCredentials(credentials) {
                openshift.verbose()
                //openshift.newBuild( "https://${githubUser}:${githubPwd}@github.com/pfeimich/qwc-service.git", "${args}")
                openshift.verbose(false)
            }
        }
    }
}
