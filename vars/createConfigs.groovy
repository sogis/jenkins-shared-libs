def call(String environment, String branch, String dbuser,String dbuserpwd, String dbserver, String dbname, String configFileName, String serviceName, String schemaName, String mapping, String schemaDirName = "") {
    if ( schemaDirName != "" ) {
        schemaDir = schemaDirName
        }
    else {
        schemaDir = mapping + '-' + serviceName
        } 
    if ( schemaName == "wms-qgs-content" || schemaName == "wfs-qgs-content" || schemaName == "print-qgs-content" || schemaName == "edit-qgs-content" ) {
        targetPath = '/data'
        githubRepo = 'sogis'
        templatePath = schemaName + '/template.json'
        }
    else {
        targetPath = '/data/config'
        githubRepo = 'qwc-services'
        templatePath = 'template.json'
        if ( serviceName == "qwc-service" ) {
            mysochTemplatePath == 'mysoch_template.json'
            }
        else {
            mysochTemplatePath == templatePath
            }
        }
    sh """ 
        if [ -d "sql2json" ]; then
          rm -rf sql2json/* rm -rf sql2json/.git
        fi
        
        if [ ! -d  "config/default" ]; then
          mkdir -p config/default
        fi

        # if not exists get the sql2json.jar and set the necessary permissions
        if [ ! -f "sql2json.jar" ]; then
          wget https://github.com/sogis/simi-sql2json/releases/download/v1.1.35/sql2json.jar
          whoami
          chmod u+x sql2json.jar
        fi

        # sql2json command to create the config file in default directory
        java -jar sql2json.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t https://raw.githubusercontent.com/sogis/pipelines/${branch}/api_webgisclient/${serviceName}/sql2json/${templatePath} -o $env.WORKSPACE/config/default/${configFileName} -s https://raw.githubusercontent.com/${githubRepo}/${schemaDir}/master/schemas/${mapping}-${schemaName}.json
        
        # sql2json command to create the config file in mysoch directory
        java -jar sql2json.jar -c jdbc:postgresql://${dbserver}:5432/${dbname} -u ${dbuser} -p ${dbuserpwd} -t https://raw.githubusercontent.com/sogis/pipelines/${branch}/api_webgisclient/${serviceName}/sql2json/${templatePath} -o $env.WORKSPACE/config/mysoch/${configFileName} -s https://raw.githubusercontent.com/${githubRepo}/${schemaDir}/master/schemas/${mapping}-${schemaName}.json

        # grep for qgis-server pod name
    """
    if ( schemaName == "wms-qgs-content" ) {
        sh """
           cp -R api_webgisclient/landreg-service/grundbuchplanauszug.qgs api_webgisclient/landreg-service/print config
        """
        }
    else if ( serviceName == "qwc-service" ) {
        if ( environment == "gdi-production" ) {
            sh """
                sed -i "s|geo-i.so.ch/analytics|geo.so.ch/analytics|" api_webgisclient/qwc-service/index.html
            """
            }
        sh """
           cp -R api_webgisclient/qwc-service/index.html config/default
        """
        }
    PODNAME= sh([script: 'oc get pods -o custom-columns=POD:.metadata.name --no-headers -n ${namespace} | grep qgis-server | grep -v -E -m 1 "featureinfo|build|print|deploy"', returnStdout: true]).trim()
    sh """
        oc rsync -n ${namespace} config/ $PODNAME:${targetPath}
    """
    dir("$env.WORKSPACE/config/default") {
        stash name: configFileName, includes: configFileName
    }
    archiveArtifacts artifacts: 'config/**', onlyIfSuccessful: true, allowEmptyArchive: true
    sh """
        rm -rf $env.WORKSPACE/config
    """
    }
