package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
			System.out.println("2 - Busca funcionários por nome, data de contratação e salário maior");
			System.out.println("3 - Busca funcionários contratados após a data");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioPorNome(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioDataContratacao(scanner);
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

	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.print("Qual o nome para pesquisar? ");
		String nome = scanner.next();

		System.out.print("Qual a data de contratação para pesquisar? ");
		LocalDate data = LocalDate.parse(scanner.next());

		System.out.print("Qual o salário mínimo para pesquisa? ");
		BigDecimal salario = scanner.nextBigDecimal();

		List<Funcionario> list = funcionarioRepository.findNomeDataContratacaoSalarioMario(nome, salario, data);

		list.forEach(System.out::println);
	}

	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.print("Digite a data de contratação para pesquisa: ");
		LocalDate data = LocalDate.parse(scanner.next());

		List<Funcionario> lista = funcionarioRepository.findDataContratacaoMaior(data);
		lista.forEach(System.out::println);
	}
}
