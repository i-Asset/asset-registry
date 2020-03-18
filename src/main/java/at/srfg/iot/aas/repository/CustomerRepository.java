package at.srfg.iot.aas.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import at.srfg.iot.aas.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

	Customer findById(long id);
}