def call(String appName, String namespace, String configFileName, String jobName, String buildNumber) {
    configMapName = sh(returnStdout: true, script: "echo $configFileName | sed 's/\\./-/g' | tr '[:upper:]' '[:lower:]'").trim()
    // Der folgende wget-Befehl funktioniert seit der Umstellung auf Active Directory nicht mehr,
    // weil es den apiUser dort nicht mehr gibt.
    // Allerdings wird diese Funktion hier seit gewisser Zeit gar nicht mehr gebraucht.
    // Falls der Code irgendwann wieder benötigt werden sollte, müsste statt dem wget-Befehl
    // stash/unstash eingesetzt werden.
    sh """
        wget -r -np -nd -erobots=off -A *.json --reject-regex '/\\*.+\\*/|auto_refresh' --no-check-certificate --auth-no-challenge --user='$apiUser' --password='$PwdApiUser'  '$JENKINS_URL/job/$jobName/$buildNumber/artifact/config/default/'
        oc create configmap $configMapName --from-file $configFileName --dry-run -o yaml | oc replace --force -n $namespace -f -
        rm -rf *.json
    """
    }
