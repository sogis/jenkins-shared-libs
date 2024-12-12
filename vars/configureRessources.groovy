def call(String appName, String stage, String repo, String environment){
    sh """
       statuscode=\$(curl -s -o /dev/null -w %{http_code} ${repo}/${appName}_resources_${environment}.params)
       curl -L ${repo}/${appName}_resources_${environment}.params.gpg -o ${appName}_resources_${environment}.params.gpg
       if (( statuscode == 200 )); then
         pwd
         echo '$env.GPG_DECRYPT_PW' | gpg --batch --import
         gpg --quiet --batch --yes --decrypt --output ${appName}_resources_${environment}.params ${appName}_resources_${environment}.params.gpg
         oc process -f ${repo}/resources.yaml -p ${appName}_resources_${environment}.params) | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f-
       fi
    """
}
