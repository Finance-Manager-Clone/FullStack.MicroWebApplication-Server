package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Currency;
import com.mycompany.myapp.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private ZonedDateTime time;

    private Currency currency;

    private CategoryDTO category;

    private ProductAccountDTO from;

    private ProductAccountDTO to;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public ProductAccountDTO getFrom() {
        return from;
    }

    public void setFrom(ProductAccountDTO from) {
        this.from = from;
    }

    public ProductAccountDTO getTo() {
        return to;
    }

    public void setTo(ProductAccountDTO to) {
        this.to = to;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", amount=" + getAmount() +
            ", time='" + getTime() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", category=" + getCategory() +
            ", from=" + getFrom() +
            ", to=" + getTo() +
            ", user=" + getUser() +
            "}";
    }
}
