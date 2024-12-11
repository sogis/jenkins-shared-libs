def call(String appName, String stage, String repo, String environment, String branch, String arguments) {
    sh """
       if [ -d "qwc_services" ]; then
         rm -rf qwc_services/* rm -rf qwc_services/.git
       fi
       git clone -b $branch https://github.com/sogis/qwc_services.git
       oc process -f ${repo}/buildconfig.yaml -p \$(curl -s ${repo}/${appName}_build_${environment}.params) | oc apply -n $stage -f- 
       oc start-build $appName -n $stage --from-repo=qwc_services --wait $arguments
       rm -rf qwc_services/* rm -rf qwc_services/.git
    """
    }
