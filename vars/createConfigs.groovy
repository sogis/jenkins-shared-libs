def call(String environment, String dbuser,String dbuserpwd, String dbserver, String dbname, String configFileName, String serviceName, String mapping, String schemaDirName = "") {
    if ( serviceName == "saml-auth" ) {
        schemaDir = schemaDirName
        }
    else {
        schemaDir = mapping + '-' + serviceName
        } 
    sh """
        if [ -d "sql2json" ]; then
          rm -rf sql2json/* rm -rf sql2json/.git
        fi
        if [ -L "/qgs-resources/config/${serviceName}" ]; then
          mkdir /qgs-resources/config/${serviceName}
        fi
        pwd
        ls -la
        wget https://github.com/simi-so/sql2json/releases/latest/download/sql2json.jar
        ls -la
        chmod u+x sql2json.jar
        java -jar sql2json.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t api_webgisclient/sql2json/templates/${serviceName}/${configFileName} -o /qgs-resources/config/${serviceName}/${configFileName} -s https://raw.githubusercontent.com/qwc-services/${schemaDir}/master/schemas/${mapping}-${serviceName}.json
        //mkdir config
        //mv ${configFileName} api_webgisclient/sql2json/templates/permissions.json config
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/config
    """
    }
