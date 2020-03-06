def call(String appName, String version, String qwc-version, String repo, String build) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            def buildConfig = openshift.process( "${repo}/buildconfig.yaml" )
            openshift.apply( buildConfig )
            def builds = openshift.selector( "bc", appName).startBuild( "--wait", "--build-arg QWC2_VERSION=${qwc-version}", "--build-arg GIT_VERSION=v${version}" )
            }
        }
    }
