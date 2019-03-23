### Microservice

### Installation Environment

Beberapa hal perlu disiapkan sebelum sebelum membuat aplikasi. diantaranya adalah 

1. Java 8 
2. IDE (Intellej IDEA, VS Code, STS)
3. Maven
4. Git

	
**Registry service**
	
- Buka [start.spring.io](https://start.spring.io) pada web browser anda
- Sesuaikan group, artifact, dan depedencies yang akan digunakan 
	- group : id.ac.ugm
	- artifact: registry-service
	- depedencies : web dan eureka server.
	
- Generate project 
- Unzip dan buka pada IDE 
- Tambahkan anotasi `@EnableEurekaServer`
	
	```java
	package com.emerio.bootcamp.registryservice;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
	
	@EnableEurekaServer
	@SpringBootApplication
	public class RegistryServiceApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(RegistryServiceApplication.class, args);
		}
	}

	```

- Properties File

  - default 
 
  	 Saat kita menjalankan aplikasi spring boot secara otomatis akan membaca file `application.properties` atau `application.yml`. berikut yang harus kita sesuaikan untuk membuat registry service (discovery).
  	 
  	```
  	spring.application.name=registry-service
    management.security.enabled=false
    server.port=8761
    
    #eureka.instance.hostname=localhost
    #eureka.server.enableSelfPreservation=false
    
    # EUREKA
    eureka.client.enabled=true
    eureka.client.healthcheck.enabled=true
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    
    logging.level.com.netflix.eureka=OFF
    logging.level.com.netflix.discovery=OFF
    
    feign.hystrix.enabled=false
	```
			
	
**Customer service**

- Buka [start.spring.io](https://start.spring.io) pada web browser anda
- Sesuaikan group, artifact, dan depedencies yang akan digunakan 
	- group : id.ac.ugm
	- artifact:customer-service
	- depedencies : web, eureka discovery, JPA, H2, Feign, Ribbon
		
- Generate project 
- Unzip dan buka pada IDE (VS Code atau STS)
- File Properties

	- default
	
	```
	server.port=8080
	spring.application.name=customer-service
	management.security.enabled=false

	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://registry-service:8761/eureka/
	#eureka.client.healthcheck.enabled=true
	eureka.client.register-with-eureka=true
	eureka.client.fetch-registry=true
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	# H2
	spring.h2.console.enabled=true
	spring.h2.console.path=/h2
	spring.h2.console.webAllowOthers=true
	
	# Datasource
	spring.datasource.url=jdbc:h2:file:~/spring/workshop/customer-service
	spring.datasource.username=sa
	spring.datasource.password=
	spring.datasource.driver-class-name=org.h2.Driver
	spring.h2.console.enabled=true
	spring.h2.console.settings.web-allow-others=true
	```
	
	- local
	
	```
	server.port=8080
	spring.application.name=customer-service
	management.security.enabled=false

	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
	#eureka.client.healthcheck.enabled=true
	eureka.client.register-with-eureka=true
	eureka.client.fetch-registry=true
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	# H2
	spring.h2.console.enabled=true
	spring.h2.console.path=/h2
	spring.h2.console.webAllowOthers=true
	
	# Datasource
	spring.datasource.url=jdbc:h2:file:~/spring/workshop/customer-service
	spring.datasource.username=sa
	spring.datasource.password=
	spring.datasource.driver-class-name=org.h2.Driver
	spring.h2.console.enabled=true
	spring.h2.console.settings.web-allow-others=true
	```

		
- Buat Entity
	
	```java
	package com.emerio.bootcamp.customerservice.entity;

	import java.sql.Timestamp;
	
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.Id;
	import javax.persistence.Lob;
	
	import com.fasterxml.jackson.annotation.JsonFormat;
	
	@Entity
	public class Customer{
	    @Id
	    @GeneratedValue
	    private Long id;
	    
	    private String username;
	
	    private String firstname;
	
	    private String lastname;
	    
	    private String email;
	    
	    private String address;
	    
	    @JsonFormat(pattern="yyyy-MM-dd")
	    private Timestamp dateofbirth;
	    
	    @Lob
	    @Column(name="customerimage", nullable=true, columnDefinition="mediumblob")
	    private byte[] customerimage;
	
	    @SuppressWarnings("unused")
		private Customer() { } // JPA only
	
	    public Customer(final String username) {
	        this.setUsername(username);
	    }
	    
	    public Customer(final String username, final String firstname, final String lastname) {
	        this.setUsername(username);
	        this.setFirstname(firstname);
	        this.setLastname(lastname);
	    }
	    
	    public Customer(final String username, final String firstname, final String lastname, final String email, final String address,
	    		final Timestamp dateofbirth
	    		) {
	        this.setUsername(username);
	        this.setFirstname(firstname);
	        this.setLastname(lastname);
	        this.setEmail(email);
	        this.setAddress(address);
	        this.setDateofbirth(dateofbirth);
	    }
	
	    public Long getId() {
	        return id;
	    }
	    
	    public void setId(Long id) {
	        this.id = id;
	    }
	
		public String getUsername() {
			return username;
		}
	
		public void setUsername(String username) {
			this.username = username;
		}
	
		public String getFirstname() {
			return firstname;
		}
	
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
	
		public String getLastname() {
			return lastname;
		}
	
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
	
		public String getEmail() {
			return email;
		}
	
		public void setEmail(String email) {
			this.email = email;
		}
	
		public String getAddress() {
			return address;
		}
	
		public void setAddress(String address) {
			this.address = address;
		}
	
		public Timestamp getDateofbirth() {
			return dateofbirth;
		}
	
		public void setDateofbirth(Timestamp dateofbirth) {
			this.dateofbirth = dateofbirth;
		}
	
		public byte[] getCustomerimage() {
			return customerimage;
		}
	
		public void setCustomerimage(byte[] customerimage) {
			this.customerimage = customerimage;
		}
	}

	```
	
- Buat Repo
	
	```java
	package com.emerio.bootcamp.customerservice.repo;
	
	import java.sql.Timestamp;
	import java.util.Optional;
	
	import com.emerio.bootcamp.customerservice.entity.Customer;
	
	//import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.repository.CrudRepository;
	
	
	public interface CustomerRepository extends CrudRepository<Customer, Long>{
		Optional<Customer> findByUsername(String username);
	} 
	```
	
- Buat Controller
	
	```java
	package com.emerio.bootcamp.customerservice.controller;

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
	
	import com.emerio.bootcamp.customerservice.entity.Customer;
	import com.emerio.bootcamp.customerservice.repo.CustomerRepository;
	
	@RestController
	public class CustomerController {
		
		private CustomerRepository customerRepository;
		
		@Autowired
		CustomerController (CustomerRepository customerRepository)
		{
			this.customerRepository = customerRepository;
		}
		
		@RequestMapping(method = RequestMethod.GET, value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<List<Customer>> all ()
		{
			return new ResponseEntity<List<Customer>>((List<Customer>) this.customerRepository.findAll(), HttpStatus.OK);
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

	```
	
- Inisiasi Data

	```java
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
	```



**Account service**

- Buka [start.spring.io](https://start.spring.io) pada web browser anda
- Sesuaikan group, artifact, dan depedencies yang akan digunakan 
	- group : id.ac.ugm
	- artifact:account-service
	- depedencies : web, config client, dan eureka discovery, JPA, H2, Feign, Ribbon
		
- Generate project 
- Unzip dan buka pada IDE (VS Code atau STS)
- File Properties

	- default

	```
	server.port=8082
	spring.application.name=account-service
	management.security.enabled=false

	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://registry-service:8761/eureka/
	#eureka.client.healthcheck.enabled=true
	eureka.client.register-with-eureka=true
	# eureka.client.fetch-registry=false
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	# RIBBON
	ribbon.eureka.enabled=true
	
	# H2
	spring.h2.console.enabled=true
	spring.h2.console.path=/h2
	spring.h2.console.webAllowOthers=true
	
	# Datasource
	spring.datasource.url=jdbc:h2:file:~/spring/workshop/account-service
	spring.datasource.username=sa
	spring.datasource.password=
	spring.datasource.driver-class-name=org.h2.Driver
	
	app.customer.id=customer-service

	```
	
	- local

	```
	server.port=8082
	spring.application.name=account-service
	management.security.enabled=false

	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
	#eureka.client.healthcheck.enabled=true
	eureka.client.register-with-eureka=true
	# eureka.client.fetch-registry=false
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	# RIBBON
	ribbon.eureka.enabled=true
	
	# H2
	spring.h2.console.enabled=true
	spring.h2.console.path=/h2
	spring.h2.console.webAllowOthers=true
	
	# Datasource
	spring.datasource.url=jdbc:h2:file:~/spring/workshop/account-service
	spring.datasource.username=sa
	spring.datasource.password=
	spring.datasource.driver-class-name=org.h2.Driver
	
	app.customer.id=customer-service

	```
		
- Buat Entity

	```java
	package com.emerio.bootcamp.accountservice.entity;

	import java.math.BigDecimal;
	
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.Id;
	
	@Entity
	public class Account {
	
	    @Id
	    @GeneratedValue
	    private Long accountId;
	    private String customerName;
	    private BigDecimal balance;
	
	    private Account() { 
	        super();
	    } 
	    
	    public Long getAccountId()
	    {
	        return this.accountId;
	    }
	
	    public void setAccountId(Long accountId)
	    {
	        this.accountId = accountId;
	    }
	
	    public String getCustomerName()
	    {
	        return this.customerName;
	    }
	
	    public void setCustomerName(String customerName)
	    {
	        this.customerName = customerName;
	    }
	
	    public BigDecimal getBalance()
	    {
	        return this.balance;
	    }
	
	    public void setBalance (BigDecimal balance)
	    {
	        this.balance = balance;
	    }
	
	}
	```
	
- Buat Repo

	```
	package com.emerio.bootcamp.accountservice.repo;

	import java.util.List;
	import java.util.Optional;
	
	import com.emerio.bootcamp.accountservice.entity.Account;
	
	import org.springframework.data.repository.CrudRepository;
	
	public interface AccountRepository extends CrudRepository<Account, Long>
	{
	    List<Account> findByCustomerName(String customerName);
	    Optional<Account> findByAccountId(Long accountId);
	}
	```
	
- Buat Controller
	
	```
	package com.emerio.bootcamp.accountservice.controller;

	import com.emerio.bootcamp.accountservice.client.CustomerServiceClient;
	import com.emerio.bootcamp.accountservice.entity.Account;
	import com.emerio.bootcamp.accountservice.repo.AccountRepository;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.MediaType;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	import java.util.List;
	import java.util.Optional;
	
	import javax.persistence.Entity;
	
	import org.springframework.web.bind.annotation.RequestMethod;
	
	@RestController
	public class AccountController {
	
	    private final AccountRepository accountRepository;
	
	
	    @Autowired
	    AccountController(AccountRepository accountRepository)
	    {
	        this.accountRepository = accountRepository;
	    }
	
	    @Autowired
	    private CustomerServiceClient customerServiceClient;
	
	
	    // get account by username
	    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{customername}", produces = MediaType.APPLICATION_JSON_VALUE)
	    ResponseEntity<List<Account>> readAccountByCustomerName(@PathVariable String customername)
	    {
	        List<Account> result = this.accountRepository.findByCustomerName(customername);
	        return new ResponseEntity<List<Account>>(result, HttpStatus.OK);
	        
	    }
	
	    // create new account 
	    @RequestMapping(method = RequestMethod.POST, value= "/accounts")
	    ResponseEntity<Object> add (@RequestBody Account input)
	    {
	        Object obj = customerServiceClient.getCustomer(input.getCustomerName());
	        if (obj != null) 
	        {
	            Account newAccount = accountRepository.save(input);
	            return new ResponseEntity<Object>(newAccount, HttpStatus.CREATED);
	        } else 
	        {
	            return new ResponseEntity<Object>(null, HttpStatus.CREATED);
	        }
	        
	    }
	
	    @RequestMapping(method = RequestMethod.PUT, value="/accounts")
	    ResponseEntity<Object> update (@RequestBody Account input)
	    {
	        Optional<Account> search = this.accountRepository.findByAccountId(input.getAccountId());
	        if (search.isPresent())
	        {
	            Account acc = search.get();
	            input.setAccountId(acc.getAccountId());
	            Account account = this.accountRepository.save(input);
	            return new ResponseEntity<Object>(account, HttpStatus.CREATED);
	        } else 
	        {
	            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
	        }
	    }
	
	    @RequestMapping(method = RequestMethod.DELETE, value="/accounts/{accountid}")
	    ResponseEntity<Object> delete (@PathVariable Long accountid)
	    {
	        Optional<Account> search = this.accountRepository.findByAccountId(accountid);
	        if (search.isPresent())
	        {
	            Account acc = search.get();
	            this.accountRepository.delete(acc);
	            return new ResponseEntity<Object>(acc, HttpStatus.OK);
	        } else 
	        {
	            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
	        }
	    }
	}
	```
	
- Feign

	```
	package com.emerio.bootcamp.accountservice.client;

	import org.springframework.cloud.openfeign.FeignClient;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	
	@FeignClient("${app.customer.id:customer-service}")
	public interface CustomerServiceClient {
	
	    @RequestMapping(method = RequestMethod.GET, value = "/customers/{username}")
	    Object getCustomer(@PathVariable("username") String username);
	}
	```
**Gateway service**
	
- Buka [start.spring.io](https://start.spring.io) pada web browser anda
- Sesuaikan group, artifact, dan depedencies yang akan digunakan 
	- group : id.ac.ugm
	- artifact:gateway-service
	- depedencies : web, eureka discovery, Zuul, Feign, Ribbon
			
- Generate project 
- Unzip dan buka pada IDE (VS Code atau STS)
- File Properties

	- default
	
	```
	spring.application.name=gateway-service
	```
	- local

	```
	spring.application.name=gateway-service-local
	```
	
- Boostrap Properties

	- default

	```
	server.port=8765
	spring.cloud.config.uri=http://config-service:8888

	# ZUUL
	zuul.prefix=/api
	#zuul customer-service
	zuul.routes.customer.path=/customers/**
	zuul.routes.customer.serviceId=customer-service
	zuul.routes.customer.stripPrefix=false
	
	#zuul account
	# zuul.routes.identity.path=/accounts/**
	# zuul.routes.identity.serviceId=account-service
	# zuul.routes.identity.stripPrefix=false
	
	
	zuul.host.socket-timeout-millis=60000
	zuul.host.connect-timeout-millis=10000
	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
	
	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://registry-service:8761/eureka/
	#eureka.instance.preferIpAddress=true
	eureka.client.registerWithEureka=true
	eureka.client.fetchRegistry=true
	#eureka.client.healthcheck.enabled=true
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	
	# RIBBON
	ribbon.eureka.enabled=true
	ribbon.ConnectTimeout=3000
	ribbon.ReadTimeout=60000
	```
	

	- local

	```
	server.port=8765
	spring.cloud.config.uri=http://localhost:8888

	# ZUUL
	zuul.prefix=/api
	#zuul customer-service
	zuul.routes.customer.path=/customers/**
	zuul.routes.customer.serviceId=customer-service-local
	zuul.routes.customer.stripPrefix=false
	
	#zuul account-service
	#zuul.routes.account.path=/accounts/**
	#zuul.routes.account.serviceId=account-service-local
	#zuul.routes.account.stripPrefix=false
	
	zuul.host.socket-timeout-millis=60000
	zuul.host.connect-timeout-millis=10000
	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
	
	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
	#eureka.instance.preferIpAddress=true
	eureka.client.registerWithEureka=true
	eureka.client.fetchRegistry=true
	#eureka.client.healthcheck.enabled=true
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	
	# RIBBON
	ribbon.eureka.enabled=true
	ribbon.ConnectTimeout=3000
	ribbon.ReadTimeout=60000
	```
	
- Konfigurasi gateway-service pada gateway-service
	
	- gateway-service.properties
	
	```
	server.port=8765

	# ZUUL
	zuul.prefix=/api
	#zuul customer-service
	zuul.routes.customer.path=/customers/**
	zuul.routes.customer.serviceId=customer-service
	zuul.routes.customer.stripPrefix=false
	
	#zuul account
	# zuul.routes.identity.path=/accounts/**
	# zuul.routes.identity.serviceId=account-service
	# zuul.routes.identity.stripPrefix=false
	
	
	zuul.host.socket-timeout-millis=60000
	zuul.host.connect-timeout-millis=10000
	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
	
	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://registry-service:8761/eureka/
	#eureka.instance.preferIpAddress=true
	eureka.client.registerWithEureka=true
	eureka.client.fetchRegistry=true
	#eureka.client.healthcheck.enabled=true
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	
	# RIBBON
	ribbon.eureka.enabled=true
	ribbon.ConnectTimeout=3000
	ribbon.ReadTimeout=60000

	```
	
	- gateway-service-local.properties

	```
	server.port=8765

	# ZUUL
	zuul.prefix=/api
	#zuul customer-service
	zuul.routes.customer.path=/customers/**
	zuul.routes.customer.serviceId=customer-service-local
	zuul.routes.customer.stripPrefix=false
	
	#zuul account-service
	#zuul.routes.account.path=/accounts/**
	#zuul.routes.account.serviceId=account-service-local
	#zuul.routes.account.stripPrefix=false
	
	zuul.host.socket-timeout-millis=60000
	zuul.host.connect-timeout-millis=10000
	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
	
	# EUREKA
	eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
	#eureka.instance.preferIpAddress=true
	eureka.client.registerWithEureka=true
	eureka.client.fetchRegistry=true
	#eureka.client.healthcheck.enabled=true
	eureka.instance.leaseExpirationDurationInSeconds=2
	eureka.instance.leaseRenewalIntervalInSeconds=1
	
	
	# RIBBON
	ribbon.eureka.enabled=true
	ribbon.ConnectTimeout=3000
	ribbon.ReadTimeout=60000
	```
	
## CI/CD

gitlab.com menyediakan tools untuk **continous integration and continous development (CI/CD)**  sehingga kita tidak perlu lagi untuk build, test, dan deploy aplikasi ke server secara manual. 

pada bootcamp ini **continous deployment CD** tidak dipraktekan karena keterbatasan server yang dapat diakses. untuk menjalankan CI/CD cukup membuat file `.gitlab-ci.yml` dan secara otomatis akan menjalankan CI/CD. berikut adalah test script yang digunakan untuk CI/CD pada bootcamp ini

```
image: docker:latest
services:
  - docker:dind

variables:
  DOCKER_DRIVER: overlay
  SPRING_PROFILES_ACTIVE: gitlab-ci

stages:
  - build
  - test
  - package

config-service-build:
  image: maven:3-jdk-8-alpine
  stage: build
  script: 
  - cd ./config-service
  - "mvn package -B"
  artifacts:
    paths:
      - ./config-service/target/*.jar

registry-service-build:
  image: maven:3-jdk-8-alpine
  stage: build
  script: 
  - cd ./registry-service
  - "mvn package -B"
  artifacts:
    paths:
      - ./registry-service/target/*.jar

customer-service-build:
  image: maven:3-jdk-8-alpine
  stage: build
  script: 
  - cd ./customer-service
  - "mvn package -B"
  artifacts:
    paths:
      - ./customer-service/target/*.jar
      
account-service-build:
  image: maven:3-jdk-8-alpine
  stage: build
  script: 
  - cd ./account-service
  - "mvn package -B"
  artifacts:
    paths:
      - ./account-service/target/*.jar

gateway-service-build:
  image: maven:3-jdk-8-alpine
  stage: build
  script: 
  - cd ./gateway-service
  - "mvn package -B"
  artifacts:
    paths:
      - ./gateway-service/target/*.jar

customer-service-build:
  image: maven:3-jdk-8-alpine
  stage: build
  script: 
  - cd ./customer-service
  - "mvn package -B"  
  artifacts:
    paths:
      - ./customer-service/target/*.jar

customer-service-test:
  image: maven:3-jdk-8-alpine
  stage: test
  script: 
  - cd ./customer-service
  - "mvn test"  

config-service-docker-build:
  stage: package
  script:
    - docker login -u gitlab-ci-token -p $CI_BUILD_TOKEN registry.gitlab.com
    - "docker build -t ${CI_REGISTRY}/${CI_PROJECT_PATH}/config-service:${CI_COMMIT_REF_NAME} ./config-service/."
    - "docker push ${CI_REGISTRY}/${CI_PROJECT_PATH}/config-service:${CI_COMMIT_REF_NAME}"

registry-service-docker-build:
  stage: package
  script:
    - docker login -u gitlab-ci-token -p $CI_BUILD_TOKEN registry.gitlab.com
    - "docker build -t ${CI_REGISTRY}/${CI_PROJECT_PATH}/registry-service:${CI_COMMIT_REF_NAME} ./registry-service/."
    - "docker push ${CI_REGISTRY}/${CI_PROJECT_PATH}/registry-service:${CI_COMMIT_REF_NAME}"

account-service-docker-build:
  stage: package
  script:
    - docker login -u gitlab-ci-token -p $CI_BUILD_TOKEN registry.gitlab.com
    - "docker build -t ${CI_REGISTRY}/${CI_PROJECT_PATH}/account-service:${CI_COMMIT_REF_NAME} ./account-service/."
    - "docker push ${CI_REGISTRY}/${CI_PROJECT_PATH}/account-service:${CI_COMMIT_REF_NAME}"

customer-service-docker-build:
  stage: package
  script:
    - docker login -u gitlab-ci-token -p $CI_BUILD_TOKEN registry.gitlab.com
    - "docker build -t ${CI_REGISTRY}/${CI_PROJECT_PATH}/customer-service:${CI_COMMIT_REF_NAME} ./customer-service/."
    - "docker push ${CI_REGISTRY}/${CI_PROJECT_PATH}/customer-service:${CI_COMMIT_REF_NAME}"

gateway-service-docker-build:
  stage: package
  script:
    - docker login -u gitlab-ci-token -p $CI_BUILD_TOKEN registry.gitlab.com
    - "docker build -t ${CI_REGISTRY}/${CI_PROJECT_PATH}/gateway-service:${CI_COMMIT_REF_NAME} ./gateway-service/."
    - "docker push ${CI_REGISTRY}/${CI_PROJECT_PATH}/gateway-service:${CI_COMMIT_REF_NAME}"
```

## Docker Compose

karena keterbatasan server, maka untuk mengecek hasil pekerjaan dari CI/CD kita menggunakan docker-compose dengan menggunakan image yang telah berhasil dibuat.
sekaligus penjelasan kenapa pada project ini membuat dua buah profile yaitu profile default dan local. pada docker ini suatu container bicara dengan container lain menggunakan service-name, sehingga kita perlu mendefinisikan service-name pada file konfigurasi. Berikut adalah file docker-compose yang digunakan.


