def call(String appName, String namespace, String configFileName) {
    configMapName = sh(returnStdout: true, script: "echo $configFileName | sed 's/\\./-/g' | tr '[:upper:]' '[:lower:]'").trim()
    sh """
        mkdir /srv/qwc_service/config
        mkdir /srv/qwc_service/legends
        PGSERVICEFILE=/var/www/pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json all
        PGSERVICEFILE=/var/www/pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json qgs
        mv /srv/qwc_service/config /srv/qwc_service/legends $env.WORKSPACE
        oc create configmap $configMapName --from-file $env.WORKSPACE/config/default/$configFileName --from-file $env.WORKSPACE/config/default/permissions.json --dry-run -o yaml | oc replace --force -n $namespace -f -
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/legends $env.WORKSPACE/config
    """
    }
