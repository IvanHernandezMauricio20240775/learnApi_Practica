package IntegracionBackFront.backfront.Controller.Categories;

import IntegracionBackFront.backfront.Entities.Categories.CategoryEntity;
import IntegracionBackFront.backfront.Models.DTO.Categories.CategoryDTO;
import IntegracionBackFront.backfront.Services.Categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CategoryActions")
public class ControllerCategory {

    @Autowired
    CategoryService serviceCategory;

    @GetMapping("/GetPageCategory")
    public ResponseEntity<Page<CategoryDTO>> getAllCategory(@RequestParam(defaultValue = "0") int Page, @RequestParam(defaultValue = "10") int size) {
      if(size <= 0 || size > 50){
          ResponseEntity.badRequest().body(Map.of(
                  "Status: ", "El tamaño de la pagina debe de estar entre 1 y 50"
          ));
             return ResponseEntity.ok(null);
      }
        Page<CategoryDTO> category = serviceCategory.getAllCategories(Page, size);

      if(category == null){
         ResponseEntity.badRequest().body(Map.of(
                 "Status", "No hay categorias encontradas"
         ));
      }

      return ResponseEntity.ok(category);
    }
}
