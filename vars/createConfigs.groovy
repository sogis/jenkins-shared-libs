def call(Map params) {
    sh """
        mkdir /srv/qwc_service/config
        mkdir /srv/qwc_service/legends
        chown www-data:www-data /srv/qwc_service/config /srv/qwc_service/legends
        PGSERVICEFILE=/var/www/.pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json all
        cp -R /srv/qwc_service/config /srv/qwc_service/legends $env.WORKSPACE
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    }
