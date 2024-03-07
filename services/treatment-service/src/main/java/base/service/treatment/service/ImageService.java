package base.service.treatment.service;

import base.service.treatment.dto.ImageRequest;
import base.service.treatment.exceptions.ImageFileException;
import jakarta.ws.rs.NotFoundException;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.imageDir}")
    private String imageDir;

    public String saveImageRequestObjectToStorage(ImageRequest imageRequest) throws ImageFileException {
        String base64Image = imageRequest.getImage();
        if (base64Image == null) {
            throw new ImageFileException("Image is empty");
        }
        if (base64Image.length() == 0) {
            throw new ImageFileException("Image is empty");
        }
        try{
            return  saveBase64ImageToStorage(base64Image);
        }catch (Exception e){
            throw new ImageFileException("Image is not valid");
        }
    }

    public String saveBase64ImageToStorage(String base64String) throws ImageFileException {
        String[] parts = base64String.split("[;]", 2);
        String[] data = parts[1].split("[,]", 2);
        if (data.length < 2){
            throw new ImageFileException("Image is not valid");
        }
        byte[] content = Base64.decode(data[1]);
        String mimeType = detectMimeType(parts[0]);
        String uniqueFilename = saveImageBitesToStorage(content, mimeType);
        return uniqueFilename;
    }

    public void deleteImage(String imageName)  {
        Path imagePath = getImageDir(imageName);

        if (Files.exists(imagePath)) {
            try {
                Files.delete(imagePath);
            } catch (IOException e) {

            }
        }
    }

    private String saveImageBitesToStorage(byte[] content, String mimeType) throws ImageFileException {
        String uniqueFilename = randomUUIDString() + "." + mimeType;
        Path uploadPath = getImageDir();
        Path filePath = uploadPath.resolve(uniqueFilename);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.write(filePath, content);
        } catch (IOException e) {
            throw new ImageFileException("Image is not valid");
        }
        return filePath.toString();
    }

    private Path getImageDir() {
        return Path.of(imageDir);
    }

    private Path getImageDir(String filename) {
        return Path.of(filename);
    }

    private String randomUUIDString() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "");
    }

    private String detectMimeType(String mimeType) throws ImageFileException {
        String[] tokens = mimeType.split("[/]", 2);
        if (tokens[1] == null) {
            throw new ImageFileException("Can not detect file mime type");
        }
        return tokens[1];
    }

    public byte[] getImage(String imageName) throws IOException {
        Path imageDirectory = getImageDir();
        Path imagePath = imageDirectory.resolve(imageName);
        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } else {
            throw new NotFoundException("Not Found Image " + imageName);
        }
    }
}