def call() {
    sh """
        pwd
        mkdir /tmp/workspace/data-service/config
        mkdir /tmp/workspace/data-service/legends
        chown www-data:www-data /tmp/workspace/data-service/config /tmp/workspace/data-service/legends
        ls -la
        PGSERVICEFILE=/var/www/.pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json service_configs
    """
    }
