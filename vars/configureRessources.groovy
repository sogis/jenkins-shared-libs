def call(String appName, String stage, String repo, String environment){
    sh """
       # Use wget to check if the URL exists
       wget --spider -q '${repo}/${appName}_resources_${environment}.params'
       if [ $? -eq 0 ]; then
         oc process -f ${repo}/resources.yaml -p \$(curl -s ${repo}/${appName}_resources_${environment}.params) | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f
       fi
    """
}
