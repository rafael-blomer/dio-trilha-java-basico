import br.com.rafaelblomer.domain.Cliente;
import br.com.rafaelblomer.domain.Conta;
import br.com.rafaelblomer.domain.ContaCorrente;
import br.com.rafaelblomer.domain.ContaPoupanca;

public class Main {
    public static void main(String[] args) {
        Cliente venilton = new Cliente();
        venilton.setNome("Venilton");

        Conta cc = new ContaCorrente(venilton);
        Conta poupanca = new ContaPoupanca(venilton);

        cc.depositar(100);
        cc.transferir(100, poupanca);

        poupanca.alterarStatusConta();

        cc.imprimirExtrato();
        poupanca.imprimirExtrato();
    }
}