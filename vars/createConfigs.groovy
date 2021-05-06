def call(String environment, String dbuser,String dbuserpwd, String dbserver, String dbname ) {
    sh """
        if [ -d "sql2json" ]; then
          rm -rf sql2json/* rm -rf sql2json/.git
        fi
        pwd
        ls -la
        git clone https://github.com/simi-so/sql2json.git
        ls -la
        chmod u+x sql2json/sql2json.jar
        sql2json/sql2json.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t sql2json/testconfigs/dataConfig.json -o dataConfig.json
        mkdir config
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/config
    """
    }
