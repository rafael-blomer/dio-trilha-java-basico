package br.com.rafaelblomer;

import br.com.rafaelblomer.interfaces.AparelhoTelefonico;
import br.com.rafaelblomer.interfaces.NavegadorInternet;
import br.com.rafaelblomer.interfaces.ReprodutorMusical;

public class Iphone implements ReprodutorMusical, AparelhoTelefonico, NavegadorInternet {

	private String modelo;
	private int capacidadeArmazenamento;
	private String sistemaOperacional;

	public Iphone(String modelo, int capacidadeArmazenamento, String sistemaOperacional) {
		this.modelo = modelo;
		this.capacidadeArmazenamento = capacidadeArmazenamento;
		this.sistemaOperacional = sistemaOperacional;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getCapacidadeArmazenamento() {
		return capacidadeArmazenamento;
	}

	public void setCapacidadeArmazenamento(int capacidadeArmazenamento) {
		this.capacidadeArmazenamento = capacidadeArmazenamento;
	}

	public String getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}

	@Override
	public void tocar() {
		System.out.println("Reproduzindo música...");
	}

	@Override
	public void pausar() {
		System.out.println("Música pausada.");
	}

	@Override
	public void selecionarMusica(String musica) {
		System.out.println("Selecionando música: " + musica);
	}

	@Override
	public void ligar(String numero) {
		System.out.println("Ligando para " + numero);
	}

	@Override
	public void atender() {
		System.out.println("Atendendo chamada...");
	}

	@Override
	public void iniciarCorreioVoz() {
		System.out.println("Iniciando correio de voz...");
	}

	@Override
	public void exibirPagina(String url) {
		System.out.println("Exibindo página: " + url);
	}

	@Override
	public void adicionarNovaAba() {
		System.out.println("Nova aba adicionada.");
	}

	@Override
	public void atualizarPagina() {
		System.out.println("Página atualizada.");
	}

}
