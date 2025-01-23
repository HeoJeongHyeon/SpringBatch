package com.example.batchtest.repository;

import com.example.batchtest.entity.PaymentEntity;
import com.example.batchtest.entity.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {

    Page<PaymentEntity> findByStatus(PaymentStatus status, Pageable pageable);



    
}
