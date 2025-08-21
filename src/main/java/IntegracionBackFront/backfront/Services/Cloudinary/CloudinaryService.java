package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

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


    public  String uploadImage(MultipartFile file) throws IOException{
        ValidateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "resource_type","auto",
                "quality","aut:good"
        ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException{
        ValidateImage(file);

        String Originalfilename = file.getOriginalFilename();
        String fileExtensions = Originalfilename.substring(Originalfilename.lastIndexOf(".")).toLowerCase();
        String uniqueFileName = "img_" + UUID.randomUUID() + fileExtensions;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "public_id", uniqueFileName,
                "use_filename", false,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "auto",
                "quality", "auto:good"
        );

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("secure_url");
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
