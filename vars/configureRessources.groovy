def call(String stage, String file, String parameters){
    sh """
       oc process $file $parameters | oc apply -n $stage -f- 
    """
}
