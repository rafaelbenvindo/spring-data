package br.com.alura.spring.data.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;

	private final FuncionarioRepository funcionarioRepository;

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Escolha a operação:");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionário pelo nome");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioPorNome(scanner);
				break;

			default:
				system = false;
				break;
			}
		}
	}

	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.print("Digite o nome para pesquisa: ");
		String nome = scanner.next();
		List<Funcionario> listaFuncionarios = funcionarioRepository.findByNomeContains(nome);

		listaFuncionarios.forEach(System.out::println);
	}
}
