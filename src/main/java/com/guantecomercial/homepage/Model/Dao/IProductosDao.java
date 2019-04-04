package com.guantecomercial.homepage.Model.Dao;

import java.util.List;
import com.guantecomercial.homepage.Model.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProductosDao extends PagingAndSortingRepository<Producto, Long> {

    // BUSCA LOS PRODUCTOS NUEVOS QUE ENCAJE CON UNA DESCRIPCION Y PERTENEZCAN A UNA
    // CATEGORIA EN ESPESIFICO
    @Query(value = "SELECT * FROM Productos WHERE estado = 'N' AND descripcion LIKE %?2% AND categoria_id = ?1 AND almacen = '1'", nativeQuery = true)
    public Page<Producto> FindAllByCategoryTag(String categoria, String descripcion, Pageable pageable);

    // BUSCA TODOS LOS PRODUCTOS QUE PERTENEZCAN A UNA CATEGORIA EN ESPESIFICO Y
    // CUYO ESTADO SEA NUEVO O ESTADO SEGUN EL PARAMETRO
    @Query(value = "SELECT * FROM Productos WHERE estado = ?2 AND descripcion LIKE %?1% AND almacen = '1'", nativeQuery = true)
    public Page<Producto> FindAllBySameTag(String descripcion, String estado, Pageable pageable);

    // BUSCA TODOS LOS PRODUCTOS ACTIVOS CON CANTIDAD EN ALMACEN
    @Query(value = "SELECT * FROM Productos WHERE estado = ?1 AND almacen = '1'", nativeQuery = true)
    public List<Producto> FindAllUsadosActivos(String estado);
}