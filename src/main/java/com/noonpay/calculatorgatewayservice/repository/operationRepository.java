package com.noonpay.calculatorgatewayservice.repository;

import com.noonpay.calculatorgatewayservice.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface operationRepository extends JpaRepository<Operation,String> {
}
