def call(List credentials String repo String stage){
    openshift.withCluster() {
        openshift.withProject( stage ) {
            withCredentials ( credentials ) { 
                def ressources = openshift.process( "${repo}/ressources.yaml" , "-p", "DB_SERVER=geodb-t.rootso.org", "-p", "PW_OGC_SERVER=${PwdOgcServer}", "-p", "GEO_DB_SERVER=geodb-t.verw.rootso.org", "-p", "PW_SOGIS_SERVICE=${PwdSogisService}" )
                openshift.apply( ressources )
            }
        }
    }
}
