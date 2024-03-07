package base.service.appointment.service;

import base.service.appointment.dto.ImageRequest;
import base.service.appointment.exceptions.ImageFileException;
import base.service.appointment.model.Employee;

import java.io.IOException;

public interface EmployeeService {

    public Iterable<Employee> findAll();

    public Employee getById(String id);

    public Employee save(Employee employee);

    public Employee update(String id, Employee employee);

    public Employee addImage(String id, ImageRequest imageRequest) throws ImageFileException;
    public byte[] getImageBytes(String imageUrlPath) throws IOException;
    public void delete(String id);
}
