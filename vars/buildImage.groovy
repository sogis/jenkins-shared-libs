def call(String appName, String version, String qwcVersion, String repo, String build, String gitlabToken, String gitlabPwd) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            def buildConfig = openshift.process( "${repo}/buildconfig.yaml" )
            openshift.apply( buildConfig )
            def builds = openshift.selector( "bc", appName).startBuild( "--wait", "--build-arg QWC2_VERSION=${qwcVersion}", "--build-arg GIT_VERSION=v${version}", "--env GIT_TOKEN=${gitlabToken}", "GIT_PASSWORD=${gitlabPwd}" )
            }
        }
    }
