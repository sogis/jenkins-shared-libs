def call(credentials){
    echo credentials
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            withCredentials ( credentials ) { 
                def pvc = openshift.process( "https://raw.githubusercontent.com/sogis/openshift-templates/master/web_gis_client/pvc_claims.yaml" ) 
                def configMap = openshift.process( "https://raw.githubusercontent.com/sogis/openshift-templates/master/web_gis_client/configMaps.yaml" , "-p", "DB_SERVER=geodb-t.rootso.org", "-p", "PW_OGC_SERVER=${PwdOgcServer}", "-p", "URL=geo-t.so.ch", "-p", "WMTS_URL=geo-wmts-t.so.ch", "-p", "IDP_URL=geo-t.so.ch", "-p", "GEO_DB_SERVER=geodb-t.verw.rootso.org", "-p", "PW_REPORT_SERVER=${PwdReportServer}", "-p", "PW_SOGIS_SERVICE=${PwdSogisService}", "-p", "PW_SOGIS_SERVICE_WRITE=${PwdSogisServiceWrite}", "-p", "PW_MSWRITE=${PwdMswrite}", "-p", "JASPER_DATASOURCE_1=geodb-t.verw.rootso.org", "-p", "NAMESPACE=gdi-devel", "-p", "ENVIRONMENT=test" )
                openshift.apply( pvc )
                openshift.apply( configMap )
                echo "hallo"
            }
        }
    }
}
