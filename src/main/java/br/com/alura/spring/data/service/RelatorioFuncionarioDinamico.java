package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {

	private final FuncionarioRepository funcionarioRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		System.out.print("Digite uma parte do nome para busca: ");
		String nome = scanner.next();

		if (nome.equalsIgnoreCase("NULL")) {
			nome = null;
		}

		System.out.print("Digite uma parte do cpf para busca: ");
		String cpf = scanner.next();

		if (cpf.equalsIgnoreCase("NULL")) {
			cpf = null;
		}

		System.out.print("Digite o salário para busca: ");
		String salarioString = scanner.next();
		
		BigDecimal salario;
		if (salarioString.equalsIgnoreCase("NULL")) {
			salario = null;
		} else {
			salario = new BigDecimal(salarioString);
		}

		System.out.print("Digite a data para busca: ");
		String data = scanner.next();

		LocalDate dataContratacao;
		if (data.equalsIgnoreCase("NULL")) {
			dataContratacao = null;
		} else {
			dataContratacao = LocalDate.parse(data, formatter);
		}

		List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification
				.where(
						SpecificationFuncionario.nome(nome)
						.or(SpecificationFuncionario.cpf(cpf))
						.or(SpecificationFuncionario.salario(salario))
						.or(SpecificationFuncionario.dataContratacao(dataContratacao))

		));
		
		funcionarios.forEach(System.out::println);
	}
}
