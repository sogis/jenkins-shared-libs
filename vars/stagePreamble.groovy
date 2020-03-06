def call(){
    def version
    def building
    def inputParams = input(
        message: 'Bitte Versionen angeben und ob das Image gebaut werden soll',
        ok: 'Start Pipeline',
        parameters: [
            string(defaultValue: '2.0.8',
                   description: 'Bitte Version auswählen',
                   name: 'version'),
            string(defaultValue: '2.0.14',
                   description: 'Bitte QWC Version auswählen',
                   name: 'qwcVersion'),
            string(defaultValue: 'ja',
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
