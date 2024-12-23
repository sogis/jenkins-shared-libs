def call(String appName, String stage, String repo, String environment, String branch, String parameter){
    sh """
       git checkout ${branch}
       ls -la
       wget --header='Authorization: token ${PwdGitUser}' $repo/resources.yaml
       statuscode=\$(curl -H 'Authorization: token ${PwdGitUser}' -s -o /dev/null -w %{http_code} ${repo}/${appName}_resources_${environment}.params)
       if (( statuscode == 200 )); then
         oc process -f resources.yaml -p ${parameter} | oc apply -n $stage -f- 
       else
         oc process -f resources.yaml | oc apply -n $stage -f-
       fi
    """
}
