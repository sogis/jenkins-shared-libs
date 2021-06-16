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

        if [ ! -d  "config/default" ]; then
          mkdir -p config/default
        fi

        # get the sql2json.jar and set the necessary permissions
        wget https://github.com/simi-so/sql2json/releases/latest/download/sql2json.jar
        chmod u+x sql2json.jar

        # sql2json command to create the config file
        java -jar sql2json.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t api_webgisclient/sql2json/templates/${serviceName}/${configFileName} -o config/default/${configFileName} -s https://raw.githubusercontent.com/qwc-services/${schemaDir}/master/schemas/${mapping}-${serviceName}.json
        
        # grep for qgis-server pod name
        ls -la
    """
        PODNAME= sh([script: 'oc get pods -o custom-columns=POD:.metadata.name --no-headers -n ${namespace} | grep qgis-server | grep -v -E -m 1 "featureinfo|build|print"', returnStdout: true]).trim()
    sh """
        cp api_webgisclient/sql2json/templates/permissions.json config/default
        oc rsync -n ${namespace} config/ $PODNAME:/data/config
    """
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/config
    """
    }
