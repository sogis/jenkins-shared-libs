def call(String appName, String stage, String repo, String environment){
    sh """
       statuscode=\$(curl -s -o /dev/null -w %{http_code} ${repo}/${appName}_resources_${environment}.params)
       if (( statuscode == 200 )); then
         echo $DB_SERVER_TEST
         oc process -f ${repo}/resources.yaml -p \$(curl -s ${repo}/${appName}_resources_${environment}.params) | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f-
       fi
    """
}
