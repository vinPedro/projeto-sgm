# Projeto SGM - Testando Dados no Banco de Dados H2

Este README explica como configurar e testar a inserção de dados no banco de dados H2, além de fornecer algumas consultas SQL para verificar se os dados foram inseridos corretamente.

## Pré-requisitos

1. **Java 21** ou superior instalado.
2. **Spring Boot** configurado com o H2 como banco de dados em memória.
3. **IDE** como IntelliJ IDEA ou VSCode para rodar o projeto.
4. O **H2 Console** deve estar habilitado para visualização e execução de consultas SQL.

## Conectando ao Banco de Dados H2

Para testar os dados inseridos na memória H2, siga os seguintes passos:

### 1. Acesse o H2 Console

1. **Inicie o Projeto**: Execute a aplicação Spring Boot no seu ambiente local.
   - O console H2 estará disponível em: `http://localhost:8080/h2-console`.
   
2. **Configuração de Conexão**:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **User Name**: `sa`
   - **Password**: (deixe em branco)

   **Nota**: `jdbc:h2:mem:testdb` é a URL de conexão para o banco de dados em memória. Se você tiver configurado um banco de dados persistente, a URL pode ser diferente.

3. **Clique em "Connect"** para se conectar ao banco de dados.

### 2. Dados Inseridos Automaticamente para Teste

Para testar as consultas SQL, alguns dados serão automaticamente inseridos ao iniciar a aplicação. 
Esses dados são inseridos pelo método `insertTestData()` da classe `TestService`, ao executar a aplicação.

