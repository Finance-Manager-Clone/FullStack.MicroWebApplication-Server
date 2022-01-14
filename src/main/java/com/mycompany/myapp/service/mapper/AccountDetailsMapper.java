package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.AccountDetails;
import com.mycompany.myapp.service.dto.AccountDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountDetails} and its DTO {@link AccountDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface AccountDetailsMapper extends EntityMapper<AccountDetailsDTO, AccountDetails> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    AccountDetailsDTO toDto(AccountDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AccountDetailsDTO toDtoId(AccountDetails accountDetails);
}
