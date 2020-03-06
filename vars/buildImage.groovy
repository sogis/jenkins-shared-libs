def call(String appName, String version, String qwcVersion, String repo, String build) {
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            def buildConfig = openshift.process( "${repo}/buildconfig.yaml" )
            openshift.apply( buildConfig )
            println ${qwcVersion}
            def builds = openshift.selector( "bc", appName).startBuild( "--wait", "--build-arg QWC2_VERSION=${qwcVersion}", "--build-arg GIT_VERSION=v${version}" )
            }
        }
    }
