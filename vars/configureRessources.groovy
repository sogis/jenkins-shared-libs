def call(List credentials, String repo, String stage, String parameters){
    openshift.withCluster() {
        openshift.withProject( stage ) {
            withCredentials ( credentials ) { 
                // def ressources = openshift.process( "${repo}/ressources.yaml" , "-p", "DB_SERVER=geodb-t.rootso.org", "-p", "PW_OGC_SERVER=${PwdOgcServer}", "-p", "GEO_DB_SERVER=geodb-t.verw.rootso.org", "-p", "PW_SOGIS_SERVICE=${PwdSogisService}", "-p", "NAMESPACE=${stage}" )
                println parameters
                def ressources = openshift.process( "${repo}/ressources.yaml", parameters)
                openshift.apply( ressources )
            }
        }
    }
}
