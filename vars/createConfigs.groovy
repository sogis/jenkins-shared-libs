def call(String environment) {
    sh """
        mkdir /srv/qwc_service/config
        mkdir /srv/qwc_service/legends
        PGSERVICEFILE=/var/www/${environment}/pg_service-${environment}.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json all
        PGSERVICEFILE=/var/www/${environment}/pg_service-${environment}.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json qgs
        mv /srv/qwc_service/config /srv/qwc_service/legends $env.WORKSPACE
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/legends $env.WORKSPACE/config
    """
    }
