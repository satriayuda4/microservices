package id.ac.ugm.accountservice.repo;

import java.util.List;
import java.util.Optional;

import id.ac.ugm.accountservice.entity.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long>
{
    List<Account> findByCustomerName(String customerName);
    Optional<Account> findByAccountId(Long accountId);
}

