def call(Map params) {
    sh """
        mkdir config
        mkdir legends
        chown www-data:www-data config legends
        PGSERVICEFILE=/var/www/.pg_service.conf python3 /srv/qwc_service/config_generator.py /srv/qwc_service/configGeneratorConfig.json all
    """
    }
