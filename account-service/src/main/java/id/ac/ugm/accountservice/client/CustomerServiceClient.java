package id.ac.ugm.accountservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("${app.customer.id:customer-service}")
public interface CustomerServiceClient
{
    @RequestMapping(method = RequestMethod.GET, value = "/customers/{username}")
    Object getCustomer(@PathVariable("username") String username);

}
