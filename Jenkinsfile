pipeline {
    agent any

    tools {
        maven 'Maven3'  // Assumindo que você configurou uma instalação do Maven chamada 'Maven3' no Jenkins.
        jdk 'JDK11'    // Assumindo que você configurou uma instalação do JDK chamada 'JDK11' no Jenkins.
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm  // Isso irá checar o código-fonte usando as configurações SCM do job.
            }
        }

        stage('Build') {
            steps {
                script {
                    // Construir o projeto usando o Maven
                    sh 'mvn clean install -DskipTests'  // '-DskipTests' para evitar executar testes nesta etapa.
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Executar testes usando o Maven
                    sh 'mvn test'
                }
            }
        }

        stage('Archive artifacts') {
            steps {
                script {
                    // Arquivar os artefatos. Aqui, estou assumindo que seu artefato é um JAR gerado na pasta 'target'.
                    // Ajuste o caminho se necessário.
                    archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                }
            }
        }
    }
}
