package id.ac.ugm.customerservice.repo;

import id.ac.ugm.customerservice.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
    Optional<Customer> findByUsername(String username);
}

