package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AccountDetailsRepository;
import com.mycompany.myapp.service.AccountDetailsService;
import com.mycompany.myapp.service.dto.AccountDetailsDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AccountDetails}.
 */
@RestController
@RequestMapping("/api")
public class AccountDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AccountDetailsResource.class);

    private static final String ENTITY_NAME = "accountDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountDetailsService accountDetailsService;

    private final AccountDetailsRepository accountDetailsRepository;

    public AccountDetailsResource(AccountDetailsService accountDetailsService, AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsService = accountDetailsService;
        this.accountDetailsRepository = accountDetailsRepository;
    }

    /**
     * {@code POST  /account-details} : Create a new accountDetails.
     *
     * @param accountDetailsDTO the accountDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountDetailsDTO, or with status {@code 400 (Bad Request)} if the accountDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-details")
    public ResponseEntity<AccountDetailsDTO> createAccountDetails(@Valid @RequestBody AccountDetailsDTO accountDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AccountDetails : {}", accountDetailsDTO);
        if (accountDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountDetailsDTO result = accountDetailsService.save(accountDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/account-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-details/:id} : Updates an existing accountDetails.
     *
     * @param id the id of the accountDetailsDTO to save.
     * @param accountDetailsDTO the accountDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the accountDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-details/{id}")
    public ResponseEntity<AccountDetailsDTO> updateAccountDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccountDetailsDTO accountDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccountDetails : {}, {}", id, accountDetailsDTO);
        if (accountDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountDetailsDTO result = accountDetailsService.save(accountDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-details/:id} : Partial updates given fields of an existing accountDetails, field will ignore if it is null
     *
     * @param id the id of the accountDetailsDTO to save.
     * @param accountDetailsDTO the accountDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the accountDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accountDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountDetailsDTO> partialUpdateAccountDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccountDetailsDTO accountDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountDetails partially : {}, {}", id, accountDetailsDTO);
        if (accountDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountDetailsDTO> result = accountDetailsService.partialUpdate(accountDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /account-details} : get all the accountDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountDetails in body.
     */
    @GetMapping("/account-details")
    public ResponseEntity<List<AccountDetailsDTO>> getAllAccountDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AccountDetails");
        Page<AccountDetailsDTO> page = accountDetailsService.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-details/:id} : get the "id" accountDetails.
     *
     * @param id the id of the accountDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-details/{id}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable Long id) {
        log.debug("REST request to get AccountDetails : {}", id);
        Optional<AccountDetailsDTO> accountDetailsDTO = accountDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountDetailsDTO);
    }

    /**
     * {@code DELETE  /account-details/:id} : delete the "id" accountDetails.
     *
     * @param id the id of the accountDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-details/{id}")
    public ResponseEntity<Void> deleteAccountDetails(@PathVariable Long id) {
        log.debug("REST request to delete AccountDetails : {}", id);
        accountDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
