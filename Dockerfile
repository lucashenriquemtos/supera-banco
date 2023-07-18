# Define a imagem base, que será usada como base para construir a imagem da sua aplicação
FROM openjdk:11-jdk

# Define o diretório de trabalho dentro da imagem
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para dentro da imagem
COPY target/banco-0.0.1-SNAPSHOT.jar app.jar

# Define o comando que será executado quando o container for iniciado
CMD ["java", "-jar", "app.jar"]
