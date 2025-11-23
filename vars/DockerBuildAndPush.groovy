def call(Map config = [:]) {

    if (!config.imageName) {
        error "ERROR: imageName is required"
    }

    if (!config.registry) {
        error "ERROR: registry is required (ex: myrepo/myimage)"
    }

    def dockerFile = config.dockerFile ?: "Dockerfile"
    def buildContext = config.context ?: "."
    def tag = config.tag ?: "latest"
    def registryCreds = config.registryCreds ?: "docker-cred"

    // Full image name
    def fullImage = "${config.registry}/${config.imageName}:${tag}"

    stage("Docker Build & Push") {

        echo "📦 Building image: ${fullImage}"

        docker.withRegistry('', registryCreds) {

            def img = docker.build(
                fullImage,
                "-f ${dockerFile} ${buildContext}"
            )

            echo "⬆️ Pushing image: ${fullImage}"
            img.push()
        }
    }

    echo "✔️ Docker build & push completed: ${fullImage}"
    return fullImage
}
