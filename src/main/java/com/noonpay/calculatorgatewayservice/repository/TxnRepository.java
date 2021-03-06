package com.noonpay.calculatorgatewayservice.repository;

import com.noonpay.calculatorgatewayservice.entity.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TxnRepository extends CrudRepository<Transaction, Integer> {

    @Query("SELECT credits FROM User e WHERE e.id >= :id")
    public double getCredits(@Param("id") int id);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.credits= (:credits) WHERE u.id >= :id")
    public void updateCredits(@Param("credits") Double credits, @Param("id") Integer id);


}
