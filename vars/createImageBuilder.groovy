def call(List credentials, String args){
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            if ( !openshift.selector("bc" , "qwc-service").exists() )
                                   // build qwc-service Image from Sourcepole Source Code
                                   // openshift.newBuild( "https://git.sourcepole.ch/ktso/somap", "--name=qwc-service", "--strategy=docker", "--context-dir=docker/wsgi-service", "--build-arg QWC2_VERSION=${QWC2_VERSION}", "--env GIT_USER=deployso", "--env GIT_PASSWORD=UwM4LdvEUTV7","--source-secret=deployso-at-gitlab-token", "build-secret=deployso-at-gitlab-token")
                withCredentials(credentials) {
                openshift.verbose()
                openshift.newBuild( "https://${githubUser}:${githubPwd}@github.com/pfeimich/qwc-service.git", "--name=qwc-service", "--strategy=docker", "--context-dir=qwc-service", "--build-arg QWC2_VERSION=${QWC2_VERSION}", "--env GIT_USER=deployso", "--env GIT_PASSWORD=UwM4LdvEUTV7") 
                //openshift.newBuild( "https://${githubUser}:${githubPwd}@github.com/pfeimich/qwc-service.git", "${args}")
                openshift.verbose(false)
            }
        }
    }
}
