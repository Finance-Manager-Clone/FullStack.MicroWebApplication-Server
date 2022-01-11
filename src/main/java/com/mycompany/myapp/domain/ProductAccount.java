package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.AccountType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductAccount.
 */
@Entity
@Table(name = "product_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "accoun_type", nullable = false)
    private AccountType accounType;

    @NotNull
    @Column(name = "opening_date", nullable = false)
    private ZonedDateTime openingDate;

    @DecimalMin(value = "0")
    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accounts" }, allowSetters = true)
    private AccountDetails accountDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public ProductAccount accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccounType() {
        return this.accounType;
    }

    public ProductAccount accounType(AccountType accounType) {
        this.setAccounType(accounType);
        return this;
    }

    public void setAccounType(AccountType accounType) {
        this.accounType = accounType;
    }

    public ZonedDateTime getOpeningDate() {
        return this.openingDate;
    }

    public ProductAccount openingDate(ZonedDateTime openingDate) {
        this.setOpeningDate(openingDate);
        return this;
    }

    public void setOpeningDate(ZonedDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public ProductAccount balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountDetails getAccountDetails() {
        return this.accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public ProductAccount accountDetails(AccountDetails accountDetails) {
        this.setAccountDetails(accountDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAccount)) {
            return false;
        }
        return id != null && id.equals(((ProductAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductAccount{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accounType='" + getAccounType() + "'" +
            ", openingDate='" + getOpeningDate() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
