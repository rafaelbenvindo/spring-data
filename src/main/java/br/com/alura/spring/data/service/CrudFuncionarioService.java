package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;

	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeRepository unidadeRepository;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
			UnidadeRepository unidadeRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeRepository = unidadeRepository;
	}

	public void inicial(Scanner scanner) {
		system = true;
		while (system) {
			System.out.println("Escolha a operação (Funcionário):");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Listar");
			System.out.println("4 - Apagar");

			int action = scanner.nextInt();
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void salvar(Scanner scanner) {
		System.out.print("Nome: ");
		scanner.nextLine();
		String nome = scanner.nextLine();
		System.out.print("CPF: ");
		String cpf = scanner.next();
		System.out.print("Salário: ");
		BigDecimal salario = scanner.nextBigDecimal();
		System.out.print("Data de contratação: ");
		LocalDate data = LocalDate.parse(scanner.next());
		System.out.print("Digite o id do cargo: ");
		int cargoId = scanner.nextInt();
		System.out.print("Digite o id das unidades separadas por vírgula: ");
		String unidades = scanner.next();

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(data);
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());

		List<Integer> unidadesL = Arrays.asList(unidades.split(",")).stream().<Integer>map(Integer::parseInt)
				.collect(Collectors.toList());

		unidadesL.forEach(unidadeId -> {
			Optional<Unidade> unidade = unidadeRepository.findById(unidadeId);
			funcionario.getUnidadeTrabalhos().add(unidade.get());
		});

		funcionarioRepository.save(funcionario);

		System.out.println("Salvo");
	}

	private void atualizar(Scanner scanner) {
		System.out.print("Id: ");
		int id = scanner.nextInt();
		Optional<Funcionario> funcionario_ = funcionarioRepository.findById(id);

		if (funcionario_.isPresent()) {
			Funcionario funcionario = funcionario_.get();

			String nome = funcionario.getNome();
			BigDecimal salario = funcionario.getSalario();

			System.out.printf("Insira o nome corrigido (%s): ", nome);
			scanner.nextLine();
			nome = scanner.nextLine();

			System.out.printf("Insira o novo salário (%s): ", salario);
			salario = scanner.nextBigDecimal();

			funcionario.setNome(nome);
			funcionario.setSalario(salario);
			funcionarioRepository.save(funcionario);
		} else {
			System.out.println("Não existe funcionário com este id.");
		}

	}

	private void visualizar(Scanner scanner) {
		System.out.print("Qual página você deseja visualizar? ");
		int page = scanner.nextInt() - 1;

		Pageable pageable = PageRequest.of(page, 2);

		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

		System.out.println(funcionarios);
		System.out.println("Página atual: " + (funcionarios.getNumber() + 1));
		System.out.println("Total de elementos: " + funcionarios.getTotalElements());

		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}

	private void deletar(Scanner scanner) {
		System.out.print("Especifique o id: ");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Deletado.");
	}

}
