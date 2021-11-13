package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private Boolean system = true;

	private final CargoRepository cargoRepository;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Escolha a operação (Cargo):");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");

			int action = scanner.nextInt();
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;

			default:
				system = false;
				break;
			}
		}
	}

	private void salvar(Scanner scanner) {
		System.out.println("Descrição do cargo");
		String descricao = scanner.next();

		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);

		System.out.println("Salvo");
	}

	private void atualizar(Scanner scanner) {
		System.out.print("Id: ");
		int id = scanner.nextInt();
		Optional<Cargo> cargo_ = cargoRepository.findById(id);

		if (cargo_.isPresent()) {
			Cargo cargo = cargo_.get();

			String descricao = cargo.getDescricao();
			System.out.printf("Nova descrição do cargo (%s): ", descricao);
			descricao = scanner.next();
			cargo.setDescricao(descricao);
			cargoRepository.save(cargo);
		} else {
			System.out.println("Não existe cargo com este id.");
		}

	}

}
