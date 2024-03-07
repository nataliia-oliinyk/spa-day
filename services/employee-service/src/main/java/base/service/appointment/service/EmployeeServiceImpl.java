package base.service.appointment.service;

import base.service.appointment.controller.EmployeesController;
import base.service.appointment.dto.ImageRequest;
import base.service.appointment.exceptions.ImageFileException;
import base.service.appointment.model.Employee;
import base.service.appointment.repository.EmployeeRepository;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesController.class);
    @Autowired
    EmployeeRepository repository;
    @Autowired
    ImageService imageService;

    private Employee findById(String id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("Not found employee: " + id);
        }
        return repository.findById(id).get();
    }

    @Override
    public Iterable<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(String id) {
        return findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee update(String id, Employee employee) {
        Employee _employee = findById(id);
        employee.setId(_employee.getId());
        return repository.save(employee);
    }

    @Override
    public Employee addImage(String id, ImageRequest imageRequest) throws ImageFileException {
        Employee employee = findById(id);
        if (employee.getImage().isEmpty()) {
            imageService.deleteImage(employee.getImage());
        }
        String filename = imageService.saveImageRequestObjectToStorage(imageRequest);
        employee.setImage( filename);
        return save(employee);
    }

    @Override
    public byte[] getImageBytes(String imageUrlPath) throws IOException {
        return imageService.getImage(imageUrlPath);
    }
    @Override
    public void delete(String id) {
        Employee employee = findById(id);
        repository.deleteById(id);
        if (employee.getImage() != null) {
            imageService.deleteImage(employee.getImage());
        }
    }

}
