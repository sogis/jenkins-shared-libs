def call(String environment, String dbuser,String dbuserpwd, String dbserver, String dbname, String configFileName, String serviceName, String schemaDirName) {
    sh """
        if [ -d "sql2json" ]; then
          rm -rf sql2json/* rm -rf sql2json/.git
        fi
        pwd
        ls -la
        wget https://github.com/simi-so/sql2json/releases/download/1.1.20/sql2json-1.1.20.jar
        ls -la
        chmod u+x sql2json-1.1.20.jar
        java -jar sql2json-1.1.20.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t api_webgisclient/sql2json/templates/${serviceName}/${configFileName} -o ${configFileName} -s https://raw.githubusercontent.com/qwc-services/qwc-${schemaDirName}/master/schemas/qwc-${serviceName}.json
        mkdir config
        mv ${configFileName} api_webgisclient/sql2json/templates/permissions.json config
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/config
    """
    }
