def call(){
    def version
    def building
    def inputParams = input(
        id: 'selectVersion', message: 'Bitte Version angeben und ob das Image gebaut werden soll',
        parameters: [
            string(defaultValue: 'None',
                   description: 'Bitte Version auswählen',
                   name: 'version'),
            choice(defaultValue: 'ja', 
                   choices: ['ja','nein'].join('\n'),
                   description: 'Soll das Image gebaut werden?'
                   name: 'build'
        ])

    version = inputParams.version
    building = inputParams.build
    echo "${version}"
    
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            echo "Using project: ${openshift.project()}"
        }
    }
    return version, building
}
