package br.edu.ifpb.sgm.projeto_sgm.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SuapService {

    private final WebClient webClient;

    public SuapService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    private static final String OBTAIN_TOKEN_URL = "https://suap.ifpb.edu.br/api/jwt/obtain_token/";

    /**
     * Valida login e senha no SUAP.
     * Retorna o token se válido, ou null se inválido.
     */
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
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
