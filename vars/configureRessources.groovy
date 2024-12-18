def call(String appName, String stage, String repo, String environment, String parameter){
    sh """
       statuscode=\$(curl -s -o /dev/null -w %{http_code} ${repo}/${appName}_resources_${environment}.params)
       if (( statuscode == 200 )); then
         oc process -f ${repo}/resources.yaml -p ${parameter} | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f-
       fi
    """
}
