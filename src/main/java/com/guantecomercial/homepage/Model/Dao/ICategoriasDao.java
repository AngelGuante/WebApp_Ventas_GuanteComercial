package com.guantecomercial.homepage.Model.Dao;

import com.guantecomercial.homepage.Model.Entity.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface ICategoriasDao extends CrudRepository<Categoria, Long> {}