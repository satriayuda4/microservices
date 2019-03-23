package id.ac.ugm.customerservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.ac.ugm.customerservice.entity.Customer;
import id.ac.ugm.customerservice.repo.CustomerRepository;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    CustomerController (CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> all ()
    {
        return new ResponseEntity<>(this.customerRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> readCustomerByUsername (@PathVariable String username)
    {
        Optional<Customer> result = this.customerRepository.findByUsername(username);
        if (result.isPresent())
        {
            return new ResponseEntity<Object>(result.get(), HttpStatus.OK);
        } else
        {
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        }
    }

}

