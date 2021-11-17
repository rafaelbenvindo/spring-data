package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.CrudUnidadeService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private Boolean system = true;

	private final CrudCargoService cargoService;
	private final CrudUnidadeService unidadeService;
	private final CrudFuncionarioService funcionarioService;

	public SpringDataApplication(CrudCargoService cargoService, CrudUnidadeService unidadeService,
			CrudFuncionarioService funcionarioService) {
		this.cargoService = cargoService;
		this.unidadeService = unidadeService;
		this.funcionarioService = funcionarioService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (system) {
			System.out.println("Digite a opção desejada:");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Unidade");
			System.out.println("3 - Funcionário");

			int action = scanner.nextInt();
			switch (action) {
			case 1:
				cargoService.inicial(scanner);
				break;

			case 2:
				unidadeService.inicial(scanner);
				break;

			case 3:
				funcionarioService.inicial(scanner);
				break;
			default:
				system = false;
				break;
			}
		}

	}

}
