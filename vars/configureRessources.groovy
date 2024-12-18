def call(String appName, String stage, String repo, String environment, String parameter){
    sh """
       statuscode=\$(curl -s -o /dev/null -w %{http_code} ${repo}/${appName}_resources_${environment}.params)
       if (( statuscode == 200 )); then
         oc process -f ${repo}/resources.yaml -p DB_SERVER=geodb-t.rootso.org GEO_DB_SERVER=geodb-t.verw.rootso.org DB_PUB=pub DB_EDIT=edit DB_OEREB=oereb_v2 DB_SOGIS=sogis USER_OGC_SERVER=ogc_server PW_OGC_SERVER=**** USER_SOGIS_SERVICE=sogis_service PW_SOGIS_SERVICE=**** USER_SOGIS_SERVICE_WRITE=sogis_service_write PW_SOGIS_SERVICE_WRITE=**** NAMESPACE=gdi-test | oc apply -n $stage -f- 
       else
         oc process -f ${repo}/resources.yaml | oc apply -n $stage -f-
       fi
    """
}
