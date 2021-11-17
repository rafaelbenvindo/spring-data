package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.UnidadeRepository;

@Service
public class CrudUnidadeService {

	private Boolean system = true;

	private final UnidadeRepository unidadeRepository;

	public CrudUnidadeService(UnidadeRepository unidadeRepository) {
		this.unidadeRepository = unidadeRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Escolha a operação (Unidade):");
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
				visualizar();
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
		System.out.println("Descrição da unidade");
		scanner.nextLine();
		String descricao = scanner.nextLine();
		System.out.println("Endereço da unidade");
		String endereco = scanner.nextLine();

		Unidade unidade = new Unidade();
		unidade.setDescricao(descricao);
		unidade.setEndereco(endereco);
		unidadeRepository.save(unidade);

		System.out.println("Salvo");
	}

	private void atualizar(Scanner scanner) {
		System.out.print("Id: ");
		int id = scanner.nextInt();
		Optional<Unidade> unidade_ = unidadeRepository.findById(id);

		if (unidade_.isPresent()) {
			Unidade unidade = unidade_.get();

			String descricao = unidade.getDescricao();
			String endereco = unidade.getEndereco();
			System.out.printf("Nova descrição da unidade (%s): ", descricao);
			scanner.nextLine();
			descricao = scanner.nextLine();

			System.out.printf("Novo endereço da unidade (%s): ", endereco);
			endereco = scanner.nextLine();

			unidade.setDescricao(descricao);
			unidade.setEndereco(endereco);
			unidadeRepository.save(unidade);
		} else {
			System.out.println("Não existe unidade com este id.");
		}

	}

	private void visualizar() {
		Iterable<Unidade> unidades = unidadeRepository.findAll();
		unidades.forEach(unidade -> System.out.println(unidade));
	}

	private void deletar(Scanner scanner) {
		System.out.print("Especifique o id: ");
		int id = scanner.nextInt();
		unidadeRepository.deleteById(id);
		System.out.println("Deletado.");
	}

}
