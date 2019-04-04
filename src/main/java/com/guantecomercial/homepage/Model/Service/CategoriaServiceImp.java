package com.guantecomercial.homepage.Model.Service;

import java.util.List;
import javax.transaction.Transactional;
import com.guantecomercial.homepage.Model.Dao.ICategoriasDao;
import com.guantecomercial.homepage.Model.Entity.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImp implements ICategoriaService {

    @Autowired
    private ICategoriasDao categoriaDao;

    @Transactional
    @Override
    public List<Categoria> FindAll() {
        return (List<Categoria>) categoriaDao.findAll();
    }

}