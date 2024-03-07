package base.service.treatment.service;

import base.service.treatment.dto.ImageRequest;
import base.service.treatment.exceptions.ImageFileException;
import base.service.treatment.model.Treatment;
import base.service.treatment.repository.TreatmentRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    @Autowired
    TreatmentRepository repository;

    @Autowired
    ImageService imageService;

    private Treatment findByName(String name) {
        if (repository.findById(name).isEmpty()) {
            throw new NotFoundException("Not found treatment: " + name);
        }
        return repository.findById(name).get();
    }

    @Override
    public Iterable<Treatment> findAll() {
        return  repository.findAll();
    }

    @Override
    public Treatment getByName(String name) {
        return findByName(name);
    }

    @Override
    public Treatment save(Treatment treatment) {
        return repository.save(treatment);
    }

    @Override
    public Treatment update(String name, Treatment treatment) {
        Treatment _treatment = findByName(name);
        treatment.setName(_treatment.getName());
        return repository.save(treatment);
    }

    @Override
    public Treatment addImage(String name, ImageRequest imageRequest) throws ImageFileException {
        Treatment treatment = findByName(name);
        if (!treatment.getImage().isEmpty()) {
            imageService.deleteImage(treatment.getImage());
        }
        String filename = imageService.saveImageRequestObjectToStorage(imageRequest);
        treatment.setImage( filename);
        return save(treatment);
    }

    @Override
    public byte[] getImageBytes(String imageUrlPath) throws IOException {
        return imageService.getImage(imageUrlPath);
    }

    @Override
    public void delete(String name) {
        Treatment treatment = findByName(name);
        repository.deleteById(name);
        if (treatment.getImage() != null){
            imageService.deleteImage(treatment.getImage());
        }
    }
}
