package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AccountDetails;
import com.mycompany.myapp.repository.AccountDetailsRepository;
import com.mycompany.myapp.service.AccountDetailsService;
import com.mycompany.myapp.service.dto.AccountDetailsDTO;
import com.mycompany.myapp.service.mapper.AccountDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountDetails}.
 */
@Service
@Transactional
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private final Logger log = LoggerFactory.getLogger(AccountDetailsServiceImpl.class);

    private final AccountDetailsRepository accountDetailsRepository;

    private final AccountDetailsMapper accountDetailsMapper;

    public AccountDetailsServiceImpl(AccountDetailsRepository accountDetailsRepository, AccountDetailsMapper accountDetailsMapper) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountDetailsMapper = accountDetailsMapper;
    }

    @Override
    public AccountDetailsDTO save(AccountDetailsDTO accountDetailsDTO) {
        log.debug("Request to save AccountDetails : {}", accountDetailsDTO);
        AccountDetails accountDetails = accountDetailsMapper.toEntity(accountDetailsDTO);
        accountDetails = accountDetailsRepository.save(accountDetails);
        return accountDetailsMapper.toDto(accountDetails);
    }

    @Override
    public Optional<AccountDetailsDTO> partialUpdate(AccountDetailsDTO accountDetailsDTO) {
        log.debug("Request to partially update AccountDetails : {}", accountDetailsDTO);

        return accountDetailsRepository
            .findById(accountDetailsDTO.getId())
            .map(existingAccountDetails -> {
                accountDetailsMapper.partialUpdate(existingAccountDetails, accountDetailsDTO);

                return existingAccountDetails;
            })
            .map(accountDetailsRepository::save)
            .map(accountDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountDetailsDTO> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all AccountDetails");
        return accountDetailsRepository.findByUserIsCurrentUser(pageable).map(accountDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDetailsDTO> findOne(Long id) {
        log.debug("Request to get AccountDetails : {}", id);
        return accountDetailsRepository.findById(id).map(accountDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountDetails : {}", id);
        accountDetailsRepository.deleteById(id);
    }
}
