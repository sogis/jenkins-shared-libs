def call(){
    def version
    def selectVersion = input(
        id: 'selectVersion', message: 'Enter version number',
        parameters: [
            string(defaultValue: 'None',
                   description: 'Version number',
                   name: 'Version')
        ])

    version = selectVersion?:''
    echo "${version}"
    //def buildImage = input(
    //    id: 'buildImage', message: 'Build Image?',
    //    parameters: [
    //        boolean(defaultValue: 'None',
    //               description: 'Auswahl ob ein Image gebaut werden soll oder nicht',
    //               name: 'Build')
    //    ])
    def INPUT_PARAMS = input message: 'Please Provide Parameters', ok: 'Next',
                                        parameters: [
                                        choice(name: 'ENVIRONMENT', choices: ['dev','qa'].join('\n'), description: 'Please select the Environment'),
                                        choice(name: 'IMAGE_TAG', choices: ['1','2'].join('\n'), description: 'Available Docker Images')]
    env.ENVIRONMENT = INPUT_PARAMS.ENVIRONMENT
    env.IMAGE_TAG = INPUT_PARAMS.IMAGE_TAG

    version = userInput?:''
    newImage = "docker-registry.default.svc:5000/gdi-devel/qwc-service:${version}"
    
    openshift.withCluster() {
        openshift.withProject('gdi-devel') {
            echo "Using project: ${openshift.project()}"
        }
    }
    return version
}
