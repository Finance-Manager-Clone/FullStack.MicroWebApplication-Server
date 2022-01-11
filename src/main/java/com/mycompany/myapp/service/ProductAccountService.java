package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProductAccountDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ProductAccount}.
 */
public interface ProductAccountService {
    /**
     * Save a productAccount.
     *
     * @param productAccountDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAccountDTO save(ProductAccountDTO productAccountDTO);

    /**
     * Partially updates a productAccount.
     *
     * @param productAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductAccountDTO> partialUpdate(ProductAccountDTO productAccountDTO);

    /**
     * Get all the productAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductAccountDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAccountDTO> findOne(Long id);

    /**
     * Delete the "id" productAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
