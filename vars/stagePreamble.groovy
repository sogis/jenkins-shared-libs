def call(){
    def version
    def building
    def inputParams = input(
        message: 'Bitte Versionen angeben und ob das Image gebaut werden soll',
        ok: 'Start Pipeline',
        parameters: [
            file(description: 'Bitte requirements.txt hochladen',
                 name: 'requirements.txt'),
            string(description: 'Bitte Tag des generierten bzw. deployten Images angeben',
                   name: 'vDeployImage'),
            string(defaultValue: "gdi-test",
                   description: 'Bitte Namespace auswählen',
                   name: 'namespace'),
            choice(choices: ['ja','nein'],
                   description: 'Soll das Image gebaut werden?',
                   name: 'build')
            ])
    
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            echo "Using project: ${openshift.project()}"
        }
    }
    return inputParams
}
