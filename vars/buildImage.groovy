def call(String appName, String version, String repo, String build, List credentials, String qwcVersion) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            def buildConfig = openshift.process( "${repo}/buildconfig.yaml" )
            openshift.apply( buildConfig )
            withCredentials(credentials) {
                if (qwcVersion) {
                    def builds = openshift.selector( "bc", appName).startBuild( "--wait", "--build-arg QWC2_VERSION=${qwcVersion}", "--build-arg GIT_VERSION=v${version}", "--env GIT_TOKEN=${gitlabToken}", "--env GIT_PASSWORD=${gitlabPwd}" )
                    }
                else {
                    def builds = openshift.selector( "bc", appName).startBuild( "--wait", "--build-arg GIT_VERSION=v${version}", "--env GIT_TOKEN=${gitlabToken}", "--env GIT_PASSWORD=${gitlabPwd}" )
                    }
                }  
            }
        }
    }
