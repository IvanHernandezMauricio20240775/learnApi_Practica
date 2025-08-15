package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class CloudinaryService {

    //Tamaño de las imagenes en MB
    private static  final  long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final String[] ALLOWED_EXTENSIONS = {".jpg",".jpeg",".png"};

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
       ValidateImage(file);
    }

    private void ValidateImage(MultipartFile file){
        if(file.isEmpty()){
            throw new IllegalArgumentException("El Archivo esta vacio");
        }

        if(file.getSize() > MAX_FILE_SIZE){
            throw  new IllegalArgumentException("Error el tamaño del Archvio excede la capacidad permitida de 5 MB");
        }

        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null){
            throw new IllegalArgumentException("Nombre de Archivo invalido");
        }

        String extensiones = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();

        if(!Arrays.asList(ALLOWED_EXTENSIONS).contains(extensiones)){
            throw new IllegalArgumentException("Solo se permiten Archivos JPG");
        }

        if(!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe de ser una imagen Valida");
        }


    }
}
