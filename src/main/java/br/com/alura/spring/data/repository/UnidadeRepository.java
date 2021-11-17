package br.com.alura.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Unidade;

@Repository
public interface UnidadeRepository extends CrudRepository<Unidade, Integer> {

}
