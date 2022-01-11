package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoryMapper.class, ProductAccountMapper.class })
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    @Mapping(target = "from", source = "from", qualifiedByName = "accountNumber")
    @Mapping(target = "to", source = "to", qualifiedByName = "accountNumber")
    TransactionDTO toDto(Transaction s);
}
