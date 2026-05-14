# Etapa de build
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

# Copia primeiro o pom pra aproveitar cache de dependências
COPY pom.xml .

RUN mvn dependency:go-offline

# Copia o restante do projeto
COPY src ./src

# Gera o jar
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/target/*.jar app.jar

# Porta usada pelo Render
EXPOSE 8080

# Sobe a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]