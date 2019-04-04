package com.guantecomercial.homepage.Model.Service;

import java.util.List;
import javax.transaction.Transactional;
import com.guantecomercial.homepage.Model.Dao.IProductosDao;
import com.guantecomercial.homepage.Model.Entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImp implements IProductoService {

    @Autowired
    private IProductosDao productoDao;

    @Transactional
    @Override
    public List<Producto> FindAll() {
        return (List<Producto>) productoDao.findAll();
    }

    @Transactional
    @Override
    public Page<Producto> FindAll(Pageable pageable) {
        return productoDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Producto> FindAllByCategoryTag(String id, String descripcion, Pageable pageable) {
        return productoDao.FindAllByCategoryTag(id, descripcion, pageable);
    }

    @Transactional
    @Override
    public Page<Producto> FindAllBySameTag(String descripcion, String estado, Pageable pageable) {
        return productoDao.FindAllBySameTag(descripcion, estado, pageable);
    }

    @Transactional
    @Override
    public Producto FindOne(Long id) {
        return productoDao.findById(id).orElse(null);
    }
    
    @Transactional
    @Override
    public void Save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    @Transactional
    public List<Producto> FindAllUsadosActivos(String estado) {
        return productoDao.FindAllUsadosActivos(estado);
    }
}