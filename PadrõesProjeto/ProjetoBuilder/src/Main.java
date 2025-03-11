import domain.Pessoa;
import domain.enuns.Sexo;

public class Main {
    public static void main(String[] args) {

        Pessoa pessoa = new Pessoa.PessoaBuilder()
                .setNome("Rafael")
                .setIdade(20)
                .setSexo(Sexo.MASCULINO)
                .build();

        System.out.println(pessoa);
    }
}