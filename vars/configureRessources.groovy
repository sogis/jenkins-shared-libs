def call(String appName, String stage, String repo, String environment){
    sh """
       oc process -f ${repo}/resources.yaml -p \$(curl -s ${repo}/${appName}_resources_${environment}.params) | oc apply -n $stage -f- 
    """
}
