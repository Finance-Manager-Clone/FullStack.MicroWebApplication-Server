package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductAccount;
import com.mycompany.myapp.service.dto.ProductAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAccount} and its DTO {@link ProductAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = { AccountDetailsMapper.class })
public interface ProductAccountMapper extends EntityMapper<ProductAccountDTO, ProductAccount> {
    @Mapping(target = "accountDetails", source = "accountDetails", qualifiedByName = "id")
    ProductAccountDTO toDto(ProductAccount s);

    @Named("accountNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "accountNumber", source = "accountNumber")
    ProductAccountDTO toDtoAccountNumber(ProductAccount productAccount);
}
