def call(String environment, String dbuser,String dbuserpwd, String dbserver, String dbname ) {
    sh """
        if [ -d "sql2json" ]; then
          rm -rf sql2json/* rm -rf sql2json/.git
        fi
        pwd
        ls -la
        git clone https://github.com/simi-so/sql2json.git
        wget https://github.com/simi-so/sql2json/releases/download/1.1.20/sql2json-1.1.20.jar
        ls -la
        chmod u+x sql2json-1.1.20.jar
        java -jar sql2json-1.1.20.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t sql2json/testconfigs/legendConfig.json -o legendConfig.json -s https://raw.githubusercontent.com/qwc-services/qwc-legend-service/master/schemas/qwc-legend-service.json
        mkdir config
        mv legendConfig.json permissions.json config
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/config
    """
    }
