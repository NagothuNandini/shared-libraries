def call(String repoUrl, String branch){
  def workingDir = "${env.WORKSPACE}"
    sh "rm -rf ${workingDir}/*"
    sh "git clone ${repoUrl} ${workingDir}"
    sh "git checkout ${branch}"
    return workingDir
}
