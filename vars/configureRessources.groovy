def call(String appName, String stage, String repo, String environment){
    sh """
       if [ ! -f "${repo}/${appName}_resources_${environment}.params" ]; then
         oc process -f ${repo}/resources.yaml -p \$(curl -s ${repo}/${appName}_resources_${environment}.params) | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f
       fi
    """
}
