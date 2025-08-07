package br.edu.ifpb.sgm.projeto_sgm;

import br.edu.ifpb.sgm.projeto_sgm.util.JwtAuthFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

import static br.edu.ifpb.sgm.projeto_sgm.util.Constants.*;

@Configuration
public class WebConfig implements WebMvcConfigurer{

   private final JwtAuthFilter jwtAuthFilter;

    public WebConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // permite sua aplicação React
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("Authorization", "Content-Type")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/instituicoes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/instituicoes/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT,"/api/instituicoes/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE,"/api/instituicoes/**").hasRole(ADMIN)

                        .requestMatchers(HttpMethod.GET,"/api/cursos/null-coordenadores/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.GET,"/api/cursos/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.POST,"/api/cursos/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT,"/api/cursos/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE,"/api/cursos/**").hasAnyRole(ADMIN)

                        .requestMatchers(HttpMethod.GET, "/api/disciplinas/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.PUT, "/api/disciplinas/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.POST, "/api/disciplinas/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/disciplinas/**").hasAnyRole(ADMIN, COORDENADOR)

                        .requestMatchers(HttpMethod.GET,"/api/processos-seletivos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/processos-seletivos/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.POST,"/api/processos-seletivos/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.PUT,"/api/processos-seletivos/**").hasAnyRole(ADMIN, COORDENADOR)

                        .requestMatchers(HttpMethod.GET,"/api/monitorias/inscricao/alunos/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.GET,"/api/monitorias/processos-seletivos/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/monitorias/inscricao/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/monitorias/inscricao/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/monitorias/inscricao/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/monitorias/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE, MONITOR)
                        .requestMatchers(HttpMethod.PUT,"/api/monitorias/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.POST,"/api/monitorias/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.DELETE,"/api/monitorias/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)

                        .requestMatchers(HttpMethod.GET, "/api/atividades/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE, MONITOR)
                        .requestMatchers(HttpMethod.PUT, "/api/atividades/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE, MONITOR)
                        .requestMatchers(HttpMethod.POST, "/api/atividades/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE, MONITOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/atividades/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)

                        .requestMatchers(HttpMethod.GET, "/api/professores/null-coordenacao/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/professores/coordenadores/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/professores/coordenadores/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/professores/coordenadores/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/professores/coordenadores/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/professores/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.POST, "/api/professores/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.PUT, "/api/professores/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/professores/**").hasAnyRole(ADMIN, COORDENADOR)

                        .requestMatchers(HttpMethod.GET, "/api/alunos/disciplinas/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.POST, "/api/alunos/disciplinas/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.DELETE, "/api/alunos/disciplina/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.GET, "/api/alunos/null-monitoria/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.GET, "/api/alunos/monitores/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.POST, "/api/alunos/monitores/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.PUT, "/api/alunos/monitores/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.DELETE, "/api/alunos/monitores/**").hasAnyRole(ADMIN, COORDENADOR, DOCENTE)
                        .requestMatchers(HttpMethod.POST, "/api/alunos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/alunos/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.PUT, "/api/alunos/**").hasAnyRole(ADMIN, COORDENADOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/alunos/**").hasAnyRole(ADMIN, COORDENADOR)

                        .requestMatchers("/api/pessoas/**").authenticated()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        http.logout(
                (logout) -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                                    throws IOException, ServletException {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("Logout realizado com sucesso!");
                            }
                        })
        );


        return http.build();
    }
}