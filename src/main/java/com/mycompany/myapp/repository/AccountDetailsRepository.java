package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AccountDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AccountDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
    @Query("select account_details from AccountDetails account_details where account_details.user.login = ?#{principal.username}")
    Page<AccountDetails> findByUserIsCurrentUser(Pageable pageable);
}
