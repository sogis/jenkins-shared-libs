def call(String appName, String stage, String branch, String environment, String arguments) {
    sh """
       if [ -d "qwc_services" ]; then
         rm -rf qwc_services/* rm -rf qwc_services/.git
       fi
       git checkout ${branch}
       git clone -b master https://github.com/sogis/qwc_services.git
       oc process -f api_webgisclient/${appName}/deployment/buildconfig.yaml --param-file api_webgisclient/${appName}/deployment/${appName}_build_${environment}.params | oc apply -n ${stage} -f- 
       oc start-build ${appName} -n ${stage} --from-repo=qwc_services --wait ${arguments}
       rm -rf qwc_services/* rm -rf qwc_services/.git
       git checkout ${branch_webgisclient}
    """
    }
