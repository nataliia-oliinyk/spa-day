package base.service.treatment.repository;

import base.service.treatment.model.Treatment;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentRepository extends CrudRepository<Treatment, String> {

}
