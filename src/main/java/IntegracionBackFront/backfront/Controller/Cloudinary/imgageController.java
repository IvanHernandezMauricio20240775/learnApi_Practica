package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ActionsImage")
@CrossOrigin
public class imgageController {

    @Autowired
    private final CloudinaryService service;

    public imgageController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file){
        try{
            //llamando al servicio para subir la imagen y obtener el Url
            String imageUrl = service.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "message","Imagen subida exitosamente",
                    "url", imageUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }

    @PostMapping("UploadImage-to-ProductsFolder")
    public ResponseEntity<?> UploadImageToFolderProducts(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder){
        try {
            String imageUrl = service.uploadImage(file,folder);
            return ResponseEntity.ok(Map.of(
                    "message: ", "Imagen Subida exitosamente",
                    "Url", imageUrl
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen" + e);
        }

    }
}
