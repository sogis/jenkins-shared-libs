# jenkins-shared-libs
AGI Shared Libraries zur Benutzung in AGI Jenkins

## Erstellen eigener Variablen und Klassen
Die Ordnerstruktur der Shared Library muss folgendermassen aufgebaut sein:
```
(root)
+- src                     # Groovy source files
|   +- org
|       +- foo
|           +- Bar.groovy  # for org.foo.Bar class
+- vars
|   +- foo.groovy          # for global 'foo' variable
|   +- foo.txt             # help for 'foo' variable
+- resources               # resource files (external libraries only)
|   +- org
|       +- foo
|           +- bar.json    # static helper data for org.foo.Bar
```
Eigene Klassen werden im *src* Ordner erstellt. Eigene Variablen im *vars* Ordner.

Weitere Infos unter https://www.jenkins.io/doc/book/pipeline/shared-libraries/

Am komfortabelsten für die Verwendung in Jenkins Pipelines ist die Verwendung von Variablen. Im 

## Einbindung der Shared Library in Jenkins Pipeline
Es gibt verschiedene Methoden Shared Libraries in die Pipeline einzubinden.

### Aufruf in Pipeline

Im folgenden werden zwei bisher im AGI getestete Methoden für deklarative Pipelines beschrieben:
##### Variante A
```
library identifier: 'jenkins-shared-libs@master', retriever: modernSCM(
    [$class: 'GitSCMSource',
     remote: 'https://github.com/sogis/jenkins-shared-libs.git'])
pipeline {
    stages ('bla') {
        steps {
            deployImage serviceName, namespace, vDeployImage, repo, replicas, resources['cpu_request'], resources['cpu_limit'], resources['memory_request'], resources['memory_limit']
            }
        }
    }
```
Die AGI Shared Library muss hier bereits vor dem *pipeline* Block eingebunden werden.

Die definierte Variable (im Beispiel *deployImage*) wird dann einfach mit den notwendigen Parametern aufgerufen. Bei dieser Variante muss die Shared Library nicht global in Jenkins definiert werden.

##### Variante B
```
pipeline {
    libraries {
        lib("jenkins-shared-libs@${env.JENKINS_SHARED_LIBS_BRANCH}")
        }
    stages {
        steps {
            deployImage serviceName, namespace, vDeployImage, repo, replicas, resources['cpu_request'], resources['cpu_limit'], resources['memory_request'], resources['memory_limit']
            }
        }
    }
```
Die AGI Shared Library wird hier innerhalb des Pipeline Blocks eingebunden. Wenn die Shared Library auf diese Art und Weise eingebunden werden soll muss sie vorher unter https://jenkins-agi-apps-production.dev.so.ch/configure unter *Global Pipeline Library* eingebunden werden. 

In den *api_webgisclient* Pipelines kommt Variante B zum Einsatz. In Variante B kann der verwendete Branch dynamisch mit einer global definierten Env Variable gesetzt werden.
### Erstellen der Shared Library unter https://jenkins-agi-apps-prodcution.dev.so.ch/configure
Dieser Schritt ist nur bei Variante B erforderlich.
![](https://github.com/sogis/jenkins-shared-libs/blob/master/defineGlobalPipelineLib.png)

1 Name der Shared Library

2 Standardmässig verwendete Version, wenn beim Einbinden der Lib keine angegeben wird.

3 Pfad zum Repository
## Weitere Infos

* https://www.jenkins.io/doc/book/pipeline/shared-libraries/
* https://devopscube.com/jenkins-shared-library-tutorial/
* https://devopscube.com/create-jenkins-shared-library/


