package IntegracionBackFront.backfront.Controller.Categories;

import IntegracionBackFront.backfront.Entities.Categories.CategoryEntity;
import IntegracionBackFront.backfront.Exceptions.Category.ExceptionCategoryNotFound;
import IntegracionBackFront.backfront.Models.DTO.Categories.CategoryDTO;
import IntegracionBackFront.backfront.Services.Categories.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CategoryActions")
@CrossOrigin
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

    @PostMapping("/InsertCategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO created = serviceCategory.insert(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updatedCategory/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updated = serviceCategory.update(id, categoryDTO);
            return ResponseEntity.ok(updated);
        } catch (ExceptionCategoryNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/DeleteCategory/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = serviceCategory.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
