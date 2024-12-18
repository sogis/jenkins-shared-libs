def call(String appName, String stage, String repo, String environment){
    sh """
       statuscode=\$(curl -s -o /dev/null -w %{http_code} ${repo}/${appName}_resources_${environment}.params)
       params=\$(curl -s ${repo}/${appName}_resources_${environment}.params)
       echo "${params}"
       echo "${DB_SERVER_TEST}
       if (( statuscode == 200 )); then
         oc process -f ${repo}/resources.yaml -p ${params} | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f-
       fi
    """
}
