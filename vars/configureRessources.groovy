def call(String appName, String stage, String branch, String environment, String parameter){
    sh """
       git checkout ${branch}
       if [ ! -f "api_webgisclient/${appName}/deployment/${appName}_resources_${environment}.params" ]; then
         oc process -f api_webgisclient/${appName}/deployment/resources.yaml | oc apply -n $stage -f-
       else
         oc process -f api_webgisclient/${appName}/deployment/resources.yaml -p ${parameter} | oc apply -n $stage -f- 
       fi
       git checkout master
    """
}
