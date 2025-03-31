def call(String appName, String stage, String environment, String parameter){
    if (environment != 'test') {
        sh "sed -i '/\${CPU_LIMIT}/d' api_webgisclient/${appName}/deployment/resources.yaml"
    }
    sh """
       if [ ! -f "api_webgisclient/${appName}/deployment/${appName}_resources_${environment}.params" ]; then
         oc process -f api_webgisclient/${appName}/deployment/resources.yaml | oc apply -n $stage -f-
       else
         oc process -f api_webgisclient/${appName}/deployment/resources.yaml -p ${parameter} | oc apply -n $stage -f- 
       fi
       git checkout ${branch_webgisclient}
    """
}
