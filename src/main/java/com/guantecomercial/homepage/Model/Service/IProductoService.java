package com.guantecomercial.homepage.Model.Service;

import java.util.List;
import com.guantecomercial.homepage.Model.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductoService {
    public List<Producto> FindAll();

    public Page<Producto> FindAll(Pageable pageable);

    public void Save(Producto producto);

    public Producto FindOne(Long i);

    public Page<Producto> FindAllByCategoryTag(String categoria, String descripcion, Pageable pageable);

    public Page<Producto> FindAllBySameTag(String descripcion, String estado, Pageable pageable);

    public List<Producto> FindAllUsadosActivos(String estado);
}