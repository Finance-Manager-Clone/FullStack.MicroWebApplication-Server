package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AccountDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.AccountDetails}.
 */
public interface AccountDetailsService {
    /**
     * Save a accountDetails.
     *
     * @param accountDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    AccountDetailsDTO save(AccountDetailsDTO accountDetailsDTO);

    /**
     * Partially updates a accountDetails.
     *
     * @param accountDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccountDetailsDTO> partialUpdate(AccountDetailsDTO accountDetailsDTO);

    /**
     * Get all the accountDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accountDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" accountDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
