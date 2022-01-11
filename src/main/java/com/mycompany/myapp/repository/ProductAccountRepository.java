package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAccountRepository extends JpaRepository<ProductAccount, Long> {}
