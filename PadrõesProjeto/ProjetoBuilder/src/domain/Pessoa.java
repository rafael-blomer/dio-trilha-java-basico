package domain;

import domain.enuns.Sexo;

public class Pessoa {
    private String nome;
    private Integer idade;
    private Sexo sexo;

    private Pessoa(PessoaBuilder builder) {
        this.nome = builder.nome;
        this.idade = builder.idade;
        this.sexo = builder.sexo;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    @Override
    public String toString() {
        return nome + " tem " + idade + " anos e é do sexo " + sexo + ".";
    }

    public static class PessoaBuilder {
        private String nome;
        private Integer idade;
        private Sexo sexo;

        public PessoaBuilder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public PessoaBuilder setIdade(Integer idade) {
            this.idade = idade;
            return this;
        }

        public PessoaBuilder setSexo(Sexo sexo) {
            this.sexo = sexo;
            return this;
        }

        public Pessoa build() {
            return new Pessoa(this);
        }
    }
}
