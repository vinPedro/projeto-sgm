package br.edu.ifpb.sgm.projeto_sgm.exception;

public class PessoaNotFoundException extends RuntimeException {
    public PessoaNotFoundException(String message) {
        super(message);
    }

    public PessoaNotFoundException() {
        super("Pessoa não encontrada.");
    }
}
