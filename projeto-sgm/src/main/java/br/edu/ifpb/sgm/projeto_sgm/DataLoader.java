package br.edu.ifpb.sgm.projeto_sgm;

import br.edu.ifpb.sgm.projeto_sgm.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TestService testService;

    @Override
    public void run(String... args) throws Exception {
        testService.insertTestData(); // Chama o método que insere os dados
    }
}

