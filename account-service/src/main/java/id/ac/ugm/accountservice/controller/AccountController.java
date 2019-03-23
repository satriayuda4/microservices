package id.ac.ugm.accountservice.controller;

import id.ac.ugm.accountservice.client.CustomerServiceClient;
import id.ac.ugm.accountservice.entity.Account;
import id.ac.ugm.accountservice.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController
{

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
