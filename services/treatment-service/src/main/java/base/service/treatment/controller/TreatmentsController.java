package base.service.treatment.controller;

import base.service.treatment.dto.ImageRequest;
import base.service.treatment.exceptions.ImageFileException;
import base.service.treatment.service.ImageService;
import base.service.treatment.service.TreatmentService;
import base.service.treatment.model.Treatment;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

@RestController
public class TreatmentsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreatmentsController.class);

    @Autowired
    TreatmentService treatmentService;

    @Autowired
    ImageService imageService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Treatment>> getAll(){
        return new ResponseEntity<>(treatmentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Treatment> addNew(@Valid @RequestBody Treatment treatment){
        return new ResponseEntity<>(treatmentService.save(treatment), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Treatment> getByName(@PathVariable String name){
        return new ResponseEntity<>(treatmentService.getByName(name), HttpStatus.OK);
    }

    @PutMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Treatment> update(@PathVariable String name, @Valid @RequestBody Treatment treatment){
        return new ResponseEntity<>(treatmentService.update(name, treatment), HttpStatus.OK);
    }

    @PostMapping("/{name}/upload")
    public ResponseEntity<Treatment> addImage(@PathVariable String name, @RequestBody @Valid ImageRequest imageRequest) throws ImageFileException {
       return new ResponseEntity<>(treatmentService.addImage(name, imageRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{name}")
    public void delete(@PathVariable String name){
        treatmentService.delete(name);
    }

    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            byte[] imageBytes = treatmentService.getImageBytes(imageName);
            String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(mimeType));
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
