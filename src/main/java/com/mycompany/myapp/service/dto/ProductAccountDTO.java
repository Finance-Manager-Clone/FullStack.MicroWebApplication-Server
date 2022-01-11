package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.AccountType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProductAccount} entity.
 */
public class ProductAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private AccountType accounType;

    @NotNull
    private ZonedDateTime openingDate;

    @DecimalMin(value = "0")
    private BigDecimal balance;

    private AccountDetailsDTO accountDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccounType() {
        return accounType;
    }

    public void setAccounType(AccountType accounType) {
        this.accounType = accounType;
    }

    public ZonedDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(ZonedDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountDetailsDTO getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetailsDTO accountDetails) {
        this.accountDetails = accountDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAccountDTO)) {
            return false;
        }

        ProductAccountDTO productAccountDTO = (ProductAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductAccountDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accounType='" + getAccounType() + "'" +
            ", openingDate='" + getOpeningDate() + "'" +
            ", balance=" + getBalance() +
            ", accountDetails=" + getAccountDetails() +
            "}";
    }
}
