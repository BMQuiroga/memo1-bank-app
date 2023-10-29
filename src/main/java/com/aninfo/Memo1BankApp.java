package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import com.aninfo.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}

	@PutMapping("/accounts/{cbu}/withdraw")
	public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		Account acc = accountService.withdraw(cbu, sum);
		transactionService.addWithdraw(cbu, sum);
		return acc;
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		Account acc = accountService.deposit(cbu, sum);
		transactionService.addDeposit(cbu, sum);
		return acc;
	}
	/* 
	@PutMapping("/accounts/{cbu}/deposit")
	public Account depositWithPromo(@PathVariable Long cbu, @RequestParam Double sum) {
		if (sum >= PROMO_MIN_DEPOSIT) {
			double bonus = sum * PROMO_PERCENTAGE;
			sum = (bonus > PROMO_MAX_GAIN) ? sum + PROMO_MAX_GAIN : sum + bonus;
		}
		Account acc = accountService.deposit(cbu, sum);
		transactionService.addDeposit(cbu, sum);
		return acc;
	}*/

	@GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accounts/{cbu}/transactions")
    public List<Transaction> getTransactionsByCbu(@PathVariable Long cbu) {
        return transactionService.findByCbu(cbu);
    }

	@Transactional
    @DeleteMapping("/transactions/{transactionId}")
    public void deleteTransactionById(@PathVariable Long transactionId) {
        transactionService.deleteById(transactionId);
        //return ResponseEntity.noContent().build();
    }

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}

	
}
