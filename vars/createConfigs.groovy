def call(String appName, String namespace, String configFileName) {
    sh """
        mkdir /srv/qwc_service/config
        mkdir /srv/qwc_service/legends
        configMapName = ${echo $configFileName | sed s/./-/g}
        PGSERVICEFILE=/var/www/pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json all
        PGSERVICEFILE=/var/www/pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json qgs
        mv /srv/qwc_service/config /srv/qwc_service/legends $env.WORKSPACE
        oc apply configmap $configMapName --from-file=$env.WORKSPACE/config/default/$configFileName -n $namespace
        oc apply configmap permission-json --from-file=$env.WORKSPACE/config/default/permissions.json -n $namespace
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/legends $env.WORKSPACE/config
    """
    }
