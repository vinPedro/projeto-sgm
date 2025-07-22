package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.PessoaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Pessoa;
import br.edu.ifpb.sgm.projeto_sgm.service.PessoaServiceImp;
import br.edu.ifpb.sgm.projeto_sgm.service.RoleServiceImp;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaControllerImp extends RestAppController {

    @Autowired
    protected PessoaServiceImp pessoaServiceImp;

    @Autowired
    protected RoleServiceImp roleServiceImp;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    private void encriptPassword(PessoaRequestDTO pessoa) {
        String encodedPassword = passwordEncoder.encode(pessoa.getSenha());
        pessoa.setSenha(encodedPassword);
    }


}
