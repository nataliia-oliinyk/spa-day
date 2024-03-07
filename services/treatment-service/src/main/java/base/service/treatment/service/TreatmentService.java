package base.service.treatment.service;

import base.service.treatment.dto.ImageRequest;
import base.service.treatment.exceptions.ImageFileException;
import base.service.treatment.model.Treatment;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface TreatmentService {

    public Iterable<Treatment> findAll();

    public Treatment getByName(String name);

    public Treatment save(Treatment treatment);
    public Treatment update(String name, Treatment treatment);

    public Treatment addImage(String name, ImageRequest imageRequest) throws ImageFileException;
    public byte[] getImageBytes(String imageUrlPath) throws IOException;
    public void delete(String name);

}
