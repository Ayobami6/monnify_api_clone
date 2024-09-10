package com.ayo.monnify_api_clone.subaccount;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubAccountRepository extends JpaRepository<SubAccount, Long> {

    Optional<SubAccount>findBySubAccountCode(String subAccountCode);
    void deleteBySubAccountCode(String subAccountCode);
    List<SubAccount>findAllByEmail(String email);

}
