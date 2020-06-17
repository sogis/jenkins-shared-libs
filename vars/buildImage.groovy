def call(String appName, String repo, String stage, String params, String configFileName, String arguments) {
    sh """
       if [ -d "qwc_services" ]; then
         rm -rf qwc_services/* rm -rf qwc_services/.git
       fi
       git clone https://github.com/sogis/qwc_services.git
    """
    unstash 'configFile'
    sh "cp config/default/* qwc_services/$appName"
    dir("qwc_services") {
    sh """
       git config user.email mpfeiffer1975@gmail.com
       git config user.name pfeimich
       git add $appName/$configFileName
       ls -la
       git commit -m 'added file'
    """
    }
    sh """   
       oc process -f $repo/buildconfig.yaml $params | oc apply -n $stage -f- 
       oc start-build $appName -n $stage --from-repo=qwc_services --wait $arguments
    """
    }
