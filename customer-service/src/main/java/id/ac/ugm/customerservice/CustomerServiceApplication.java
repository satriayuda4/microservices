package id.ac.ugm.customerservice;

import id.ac.ugm.customerservice.entity.Customer;
import id.ac.ugm.customerservice.repo.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner init(CustomerRepository customerRepository) {
		return (evt) -> Arrays.asList(
				"adi,mufti,raka,yuke,sam,andre".split(","))
				.forEach(
						a -> {
							@SuppressWarnings("unused")
							Customer customer = customerRepository.save(new Customer(a, a, "", a + "@emeriocorp.com", "alamat rumah " + a,
									new Timestamp(System.currentTimeMillis())));
						});
	}


}
