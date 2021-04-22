package br.com.valtergodoy.testSpring.controller;

import br.com.valtergodoy.testSpring.controller.dto.CustomerRequestBody;
import br.com.valtergodoy.testSpring.model.Customer;
import br.com.valtergodoy.testSpring.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/")
    @Operation(summary = "List all Customers", tags={"Simple User", "Admin User"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    public ResponseEntity<List<Customer>> listAll() throws Exception {
        return ResponseEntity.ok(customerService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve the Customer by ID", tags={"Simple User", "Admin User"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.customerService.findById(id));
    }

    @GetMapping(path = "/find")
    @Operation(summary = "Retrieve list of Customers matching by name", tags={"Simple User", "Admin User"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    public ResponseEntity<List<Customer>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.findByName(name));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Insert Customer to DataBase", tags={"Admin User"})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    public ResponseEntity<Customer> insert(@RequestBody @Valid CustomerRequestBody customerRequestBody) {
        return new ResponseEntity<>(customerService.insert(customerRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update Customer", tags={"Admin User"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    public ResponseEntity<Customer> update(@RequestBody @Valid CustomerRequestBody customerRequestBody) {
        return new ResponseEntity<>(customerService.update(customerRequestBody), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete Customer from DataBase", tags={"Admin User"})
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Success")})
    public ResponseEntity<Void> delete(@PathVariable long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

