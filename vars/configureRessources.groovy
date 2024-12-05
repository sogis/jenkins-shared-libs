def call(String stage, String file, String parameters){
    sh """
       ls -la
       pwd
       oc process $file $parameters | oc apply -n $stage -f- 
    """
}
