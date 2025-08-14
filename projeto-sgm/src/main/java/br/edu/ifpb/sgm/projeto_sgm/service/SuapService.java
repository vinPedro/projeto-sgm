package br.edu.ifpb.sgm.projeto_sgm.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class SuapService {

    private final WebClient webClient;

    public SuapService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    private static final String OBTAIN_TOKEN_URL = "https://suap.ifpb.edu.br/api/jwt/obtain_token/";
    private static final String VALIDATE_TOKEN_URL = "https://suap.ifpb.edu.br/api/jwt/verify_token/";
    private static final String STUDENT_SEARCH_URL = "https://suap.ifpb.edu.br/api/ensino/alunos/v1/";
    private static final String TEACHER_SEARCH_URL = "https://suap.ifpb.edu.br/api/recursos-humanos/servidores/v1/";


    public String validarLogin(String matricula, String senha) {
        try {
            JsonNode response = webClient.post()
                    .uri(OBTAIN_TOKEN_URL)
                    .header("Content-Type", "application/json")
                    .bodyValue("{\"username\":\"" + matricula + "\", \"password\":\"" + senha + "\"}")
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (response != null && response.has("access")) {
                return response.get("access").asText();
            } else {
                throw new RuntimeException("Matrícula ou senha inválida no SUAP.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao autenticar no SUAP: " + e.getMessage());
        }
    }


    public void validarToken(String token) {
        try {
            webClient.post()
                    .uri(VALIDATE_TOKEN_URL)
                    .header("Content-Type", "application/json")
                    .bodyValue(Map.of("token", token))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            // Captura 4xx e 5xx da API SUAP
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Token inválido ou expirado do SUAP.");
            }
            throw new RuntimeException("Erro ao validar token do SUAP: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro de comunicação com o SUAP: " + e.getMessage());
        }
    }

    public JsonNode buscarUsuario(String token, String search, String url) {
        try {
            return webClient.get()
                    .uri(url + "?search=" + search)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Erro ao buscar aluno no SUAP: " + e.getMessage());
        }
    }

    public void validarNomeAluno(String token, String matricula, String nomeDTO) {
        JsonNode alunoJson = buscarUsuario(token, matricula, STUDENT_SEARCH_URL);
        if (alunoJson == null || !alunoJson.has("results") || alunoJson.get("results").size() == 0) {
            throw new RuntimeException("Aluno não encontrado no SUAP.");
        }

        String nomeSuap = alunoJson.get("results").get(0).get("nome").asText();
        if (!nomeSuap.equalsIgnoreCase(nomeDTO)) {
            throw new RuntimeException("Nome do aluno não confere com o SUAP.");
        }
    }

    public void validarNomeProfessor(String token, String matricula, String nomeDTO) {
        JsonNode professorJson = buscarUsuario(token, matricula, TEACHER_SEARCH_URL);
        if (professorJson == null || !professorJson.has("results") || professorJson.get("results").size() == 0) {
            throw new RuntimeException("Professor não encontrado no SUAP.");
        }

        String nomeSuap = professorJson.get("results").get(0).get("nome").asText();
        if (!nomeSuap.equalsIgnoreCase(nomeDTO)) {
            throw new RuntimeException("Nome do professor não confere com o SUAP.");
        }
    }
}
