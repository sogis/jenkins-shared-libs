def call(){
    def version
    def userInput = input(
        id: 'userInput', message: 'Enter version number',
        parameters: [
            string(defaultValue: 'None',
                   description: 'Version number',
                   name: 'Version')
        ])

    version = userInput?:''
    echo "${version}"
    newImage = "docker-registry.default.svc:5000/gdi-devel/qwc-service:${version}"
    
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            echo "Using project: ${openshift.project()}"
        }
    }
    return ${version}
}
