def call(String appName, String repo, String stage, String params, String configFileName, String jobName, String buildNumber, String arguments) {
    sh """
       pwd
       if [ -d "qwc_services" ]; then
         rm -rf qwc_services/* rm -rf qwc_services/.git
       fi
       git clone https://github.com/sogis/qwc_services.git
       git config user.email agi.info@bd.so.ch
       git config user.name agiuser
    """
    dir("qwc_services") {
    if ( appName == "qwc-service" ) {
       if ( repo == "gdi" ) {
          sh """
             sed -i "s|geo-i.so.ch/analytics|geo.so.ch/analytics|" $appName/index.html
             git add $appName/index.html
          """
          }
       }
    sh """
       wget -r -np -nd -erobots=off -A '*.json' --reject-regex '/\\*.+\\*/|auto_refresh' --no-check-certificate --auth-no-challenge --user='$apiUser' --password='$PwdApiUser'  '$JENKINS_URL/job/$jobName/$buildNumber/artifact/config/default/' -P $appName
       ls -la data-service

       git add $appName/$configFileName $appName/permissions.json
       ls -la $appName
       git commit -m 'added files'
    """
    }
    sh """   
       oc process -f $repo/buildconfig.yaml $params | oc apply -n $stage -f- 
       oc start-build $appName -n $stage --from-repo=qwc_services --wait $arguments
    """
    }
