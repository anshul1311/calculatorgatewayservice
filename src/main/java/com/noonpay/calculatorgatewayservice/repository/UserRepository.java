package com.noonpay.calculatorgatewayservice.repository;

import com.noonpay.calculatorgatewayservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT credits FROM User e WHERE e.id >= :id")
    public double getCredits(@Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.credits= (:credits) WHERE u.id >= :id")
    public void updateCredits(@Param("credits") Double credits, @Param("id") Integer id);

}
