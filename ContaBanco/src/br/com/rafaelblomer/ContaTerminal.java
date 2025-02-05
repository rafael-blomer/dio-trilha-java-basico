package br.com.rafaelblomer;

import java.util.Scanner;

public class ContaTerminal {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Integer numero;
		Float saldo;
		String nomeCliente, agencia;
		
		System.out.println("Por favor, digite o número da Agência!");
		agencia = sc.next();
		System.out.println("Agora insira o nome do cliente.");
		nomeCliente = sc.next();
		System.out.println("Insira o número da conta.");
		numero = sc.nextInt();
		System.out.println("Por fim insira o saldo da conta.");
		saldo = sc.nextFloat();
		
		System.out.println("Olá " + nomeCliente + ", obrigado por criar uma conta em nosso banco"
				+ ", sua agência é " + agencia + ", conta " + numero + " e seu saldo " 
				+ saldo + " já está disponível para saque.");
		
		sc.close();
	}

}
