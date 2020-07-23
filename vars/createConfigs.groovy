def call(Map params) {
    sh """
        mkdir /srv/qwc_service/config
        mkdir /srv/qwc_service/legends
        mkdir $env.WORKSPACE/requirements
        chown www-data:www-data /srv/qwc_service/config /srv/qwc_service/legends
        PGSERVICEFILE=/var/www/.pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json all
        cp /srv/qwc_service/requirementsImages.txt $env.WORKSPACE/requirements
        mv /srv/qwc_service/config /srv/qwc_service/legends $env.WORKSPACE
    """
    stash name: 'requirements', includes: 'requirements/**'
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/legends $env.WORKSPACE/config
    """
    }
