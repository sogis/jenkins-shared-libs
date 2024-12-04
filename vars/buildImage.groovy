def call(String appName, String repo, String stage, String params, String configFileName, String jobName, String buildNumber, String branch, String arguments) {
    sh """
       pwd
       if [ -d "qwc_services" ]; then
         rm -rf qwc_services/* rm -rf qwc_services/.git
       fi
       git clone -b $branch https://github.com/sogis/qwc_services.git
       oc process -f $repo/buildconfig.yaml $params | oc apply -n $stage -f- 
       oc start-build $appName -n $stage --from-repo=qwc_services --wait $arguments
       rm -rf qwc_services/* rm -rf qwc_services/.git
    """
    }
