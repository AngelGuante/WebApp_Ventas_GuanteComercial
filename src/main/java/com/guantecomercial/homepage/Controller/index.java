package com.guantecomercial.homepage.Controller;

import java.util.List;
import com.guantecomercial.homepage.Model.Entity.Categoria;
import com.guantecomercial.homepage.Model.Entity.Producto;
import com.guantecomercial.homepage.Model.Service.ICategoriaService;
import com.guantecomercial.homepage.Model.Service.IProductoService;
import com.guantecomercial.homepage.util.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class index {

    @Autowired
    private IProductoService ProductoService;
    @Autowired
    private ICategoriaService CategoriaService;

    private int CantidadArticolosAMostrar = 12;
    // OBJETOS PARA COMPARTIRSE ENTRE PAGINAS.
    private List<Categoria> CategoriasProductos = null;
    private List<Producto> ProductosUsados = null;
    // PARA QUE EL FORMULARIO
    private Producto productoObjetoForm = new Producto();
    private Categoria categoriaObjetoForm = new Categoria();
    // PARA GUARDAR LOS QUERYS
    private Page<Producto> productos;

    // PAGINA INICIAL CUANDO SE CARGA LA PRIMERA VEZ.
    @GetMapping("/")
    public String GetIndex(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        ProductosUsados = ProductoService.FindAllUsadosActivos("U");
        CategoriasProductos = CategoriaService.FindAll();
        Pageable pageRequest = PageRequest.of(page, CantidadArticolosAMostrar);
        productos = ProductoService.FindAllBySameTag("", "N", pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("/#formulario", productos);

        // LIMPIAR CAMPOS
        productoObjetoForm.setDescripcion("");
        categoriaObjetoForm.setId((long) 1);
        productoObjetoForm.setCategoria(categoriaObjetoForm);
        ReduceCode(model, productoObjetoForm, pageRender, productos, ProductosUsados, CategoriasProductos);
        return "/index";
    }

    // PARA CUANDO EL FORMULARIO QUE BUSCAR LOS ARTICULOS POR CATEGORIA Y
    // DESCRIPCION SE MANDE AL SERVER.
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String GetIndexParameterMethod(Producto product, Model model,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        categoriaObjetoForm = product.getCategoria();
        productoObjetoForm.setDescripcion(product.getDescripcion());
        productoObjetoForm.setDescripcion(
                (productoObjetoForm.getDescripcion().length() == 0) ? "Todo" : productoObjetoForm.getDescripcion());
        return "redirect:/" + categoriaObjetoForm.getId() + "/" + productoObjetoForm.getDescripcion() + "/";
    }

    // PARA CUANDO SE CAMBIA DE PAGINATOR MIENTRAS SE HACE UNA BUSQUEDA POR EL
    // FORMULARIO
    @GetMapping(value = "/{Categoria}/{descripcion}/")
    public String GetIndexParameter(@PathVariable(value = "Categoria") String Categoria, Model model,
            @PathVariable(value = "descripcion") String descripcion,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, CantidadArticolosAMostrar);

        // SI DESCRIPCION ES 'Todo', se quita y se pone vacio para que el query funcione
        descripcion = descripcion.equals("Todo") ? "" : descripcion;
        if (Categoria.equals("1")) {
            productos = ProductoService.FindAllBySameTag(descripcion, "N", pageRequest);
        } else
            productos = ProductoService.FindAllByCategoryTag(Categoria, descripcion, pageRequest);

        // SI DESCRIPCION ESTA VACIO, SE LE PONE 'Todo' PARA QUE LA URL NO APAREZCA
        // VACIA
        PageRender<Producto> pageRender = new PageRender<>(
                "/" + Categoria + "/" + (descripcion.length() == 0 ? "Todo" : descripcion) + "/" + "#formulario",
                productos);
        productoObjetoForm.setDescripcion(descripcion);
        categoriaObjetoForm.setId(Long.parseLong(Categoria));
        productoObjetoForm.setCategoria(categoriaObjetoForm);
        ReduceCode(model, productoObjetoForm, pageRender, productos, this.ProductosUsados, this.CategoriasProductos);
        return "/index";
    }

    // PARA REDUCIR EL CODIGO DE LAS TRES PAGINAS
    private void ReduceCode(Model model, Producto productoObjetoForm, PageRender<Producto> pageRender,
            Page<Producto> productos, List<Producto> ProductosUsados, List<Categoria> CategoriasProductos) {
        model.addAttribute("product", productoObjetoForm);
        model.addAttribute("page", pageRender);
        model.addAttribute("Producto", productos);
        model.addAttribute("ProductosUsados", ProductosUsados);
        model.addAttribute("lista", CategoriasProductos);
        System.out.println(ProductosUsados.size() > 0);
    }
}