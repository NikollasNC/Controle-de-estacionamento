package sistema;

import java.util.Scanner;

public class AreaDeSelecao {
	static final float valorHoraVaga = 6.00f;

	public static void main(String[] args) {

		// Alunos: Nikolas Cardone de Jesus e Bismarck Belizario Mafra

		Scanner leitor = new Scanner(System.in);

		byte opcao = 0;

		// Area do menu.
		do {
			System.out.println("Escolha uma opção:");
			System.out.println("[1]: Cadastro");
			System.out.println("[2]: Saída");
			System.out.println("[3]: Relatório");
			System.out.println("[4]: Finalizar sistema");

			opcao = leitor.nextByte();
			System.out.println();

			switch (opcao) {
			case 1:
				cadastrar();
				break;
			case 2:
				sair();
				break;
			case 3:
				gerarRelatorio();
				break;
			case 4:
				break;
			default:
				System.out.println("Opção inválida.");
				System.out.println();
			}

		} while (opcao != 4);

		leitor.close();
	}

	// Matriz dos dados.
	static String[][] dados = new String[100][4];
	static int[] saidas = new int[100];

	static byte contagemDeCadastros = 0;
	static byte contagemDeSaidas = 0;

	public static void cadastrar() {
		Scanner leitor = new Scanner(System.in);
		int quantidadeLetras = 0;

		System.out.print("Informe o nome do motorista: ");
		dados[contagemDeCadastros][0] = leitor.next();

		do {
			System.out.print("Informe as 7 caracteres da placa do carro: ");
			dados[contagemDeCadastros][1] = leitor.next();
			quantidadeLetras = dados[contagemDeCadastros][1].length();

			if (quantidadeLetras != 7 || placasIguais(dados[contagemDeCadastros][1])) {

				System.out.println("Placa inválida ou já registrada.");
			}
		} while (quantidadeLetras != 7 || placasIguais(dados[contagemDeCadastros][1]));

		System.out.print("hora de entrada: ");
		dados[contagemDeCadastros][2] = leitor.next();

		System.out.println("MOTORISTA CADASTRADO!");
		System.out.println();
		contagemDeCadastros++;
	}

	public static void sair() {
		Scanner leitor = new Scanner(System.in);
		int quantidadeLetras = 0;
		int horas = 0;
		String placa = "";
		boolean encontrou = true;

		// verifica se a placa tem 7 caracteres
		do {
			System.out.print("Informe a placa do carro à fazer a saída: ");
			placa = leitor.next();
			quantidadeLetras = placa.length();

			if (quantidadeLetras != 7) {

				System.out.println("Placa inválida, digite novamente.");
			}

		} while (quantidadeLetras != 7);
		
		if (saidasIguais(placa)) {
			System.out.println("Carro já efetuado a saída.");
			System.out.println();
		}
			
		for (int i = 0; i < contagemDeCadastros && !saidasIguais(placa); i++) {

			if (dados[i][1].equals(placa)) {
				System.out.print("Informe a hora da saída: ");
				dados[i][3] = leitor.next();

				String entradaSplit[] = dados[i][2].split(":");
				String saidasSplit[] = dados[i][3].split(":");

				horas += Integer.parseInt(saidasSplit[0]) - Integer.parseInt(entradaSplit[0]);

				// tolerancia de 15 minutos
				if ((Integer.parseInt(saidasSplit[1]) - Integer.parseInt(entradaSplit[1])) > 15) {
					horas++;
				}

				System.out.print("Motorista: " + dados[i][0] + " | ");
				System.out.print("Placa do carro: " + dados[i][1] + " | ");
				System.out.print("Hora de entrada: " + dados[i][2] + " | ");
				System.out.println("Hora de Saída: " + dados[i][3]);
				System.out.printf("Total a pagar: R$ %.2f %n", (float) (horas * valorHoraVaga));
				System.out.println();

				saidas[contagemDeSaidas] = i;
				contagemDeSaidas++;
				encontrou = false;
			} 
		}
		
		if (!saidasIguais(placa)) {
			if (encontrou) {
				System.out.println("Carro não encontrado.");
				System.out.println();
			}
		} 
	}

	public static void gerarRelatorio() {

		if (contagemDeSaidas > 0) {
			int horas = 0;

			for (int i = 0; i < contagemDeSaidas; i++) {
				String entradaSplit[] = dados[saidas[i]][2].split(":");
				String saidasSplit[] = dados[saidas[i]][3].split(":");

				horas += Integer.parseInt(saidasSplit[0]) - Integer.parseInt(entradaSplit[0]);

				// tolerancia de 15 minutos
				if ((Integer.parseInt(saidasSplit[1]) - Integer.parseInt(entradaSplit[1])) > 15) {
					horas++;
				}

			}

			System.out.println("Total de veiculos estacionados no dia: " + contagemDeSaidas);
			System.out.println("Tempo médio de permanência dos veículos no estacionamento: " + horas / contagemDeSaidas
					+ " hora(s).");
			System.out.printf("Valor total arrecadado: R$ %.2f %n", (float) (horas * valorHoraVaga));
			System.out.println("Carro(s) no estacionamento atualmente: " + (contagemDeCadastros - contagemDeSaidas));
			System.out.println();
		} else {
			System.out.println("Ainda não houveram saidas para gerar o relatório.");
			System.out.println();
		}
	}

	public static boolean placasIguais(String a) {

		for (int i = 0; i < contagemDeCadastros; i++) {
			if (dados[i][1].equals(a)) {
				return true;
			}
		}

		return false;
	}

	public static boolean saidasIguais(String a) {

		for (int i = 0; i < contagemDeSaidas; i++) {
			if (dados[saidas[i]][1].equals(a)) {
				return true;
			}
		}

		return false;
	}
}
