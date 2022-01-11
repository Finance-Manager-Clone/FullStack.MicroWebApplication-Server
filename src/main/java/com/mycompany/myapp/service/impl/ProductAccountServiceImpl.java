package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ProductAccount;
import com.mycompany.myapp.repository.ProductAccountRepository;
import com.mycompany.myapp.service.ProductAccountService;
import com.mycompany.myapp.service.dto.ProductAccountDTO;
import com.mycompany.myapp.service.mapper.ProductAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductAccount}.
 */
@Service
@Transactional
public class ProductAccountServiceImpl implements ProductAccountService {

    private final Logger log = LoggerFactory.getLogger(ProductAccountServiceImpl.class);

    private final ProductAccountRepository productAccountRepository;

    private final ProductAccountMapper productAccountMapper;

    public ProductAccountServiceImpl(ProductAccountRepository productAccountRepository, ProductAccountMapper productAccountMapper) {
        this.productAccountRepository = productAccountRepository;
        this.productAccountMapper = productAccountMapper;
    }

    @Override
    public ProductAccountDTO save(ProductAccountDTO productAccountDTO) {
        log.debug("Request to save ProductAccount : {}", productAccountDTO);
        ProductAccount productAccount = productAccountMapper.toEntity(productAccountDTO);
        productAccount = productAccountRepository.save(productAccount);
        return productAccountMapper.toDto(productAccount);
    }

    @Override
    public Optional<ProductAccountDTO> partialUpdate(ProductAccountDTO productAccountDTO) {
        log.debug("Request to partially update ProductAccount : {}", productAccountDTO);

        return productAccountRepository
            .findById(productAccountDTO.getId())
            .map(existingProductAccount -> {
                productAccountMapper.partialUpdate(existingProductAccount, productAccountDTO);

                return existingProductAccount;
            })
            .map(productAccountRepository::save)
            .map(productAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAccounts");
        return productAccountRepository.findAll(pageable).map(productAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAccountDTO> findOne(Long id) {
        log.debug("Request to get ProductAccount : {}", id);
        return productAccountRepository.findById(id).map(productAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAccount : {}", id);
        productAccountRepository.deleteById(id);
    }
}
