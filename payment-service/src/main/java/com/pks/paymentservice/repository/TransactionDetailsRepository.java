package com.pks.paymentservice.repository;

import com.pks.paymentservice.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionDetailsRepository
        extends JpaRepository<TransactionDetails,Long> {

    Optional<TransactionDetails> findByOrderId(Long order_id);
}
