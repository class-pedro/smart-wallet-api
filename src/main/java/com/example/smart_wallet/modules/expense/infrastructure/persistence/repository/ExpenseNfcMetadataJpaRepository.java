package com.example.smart_wallet.modules.expense.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.expense.application.port.out.ExpenseNfcMetadataRepository;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseNfcMetadataJpaRepository
        extends JpaRepository<ExpenseNfcMetadata, UUID>, ExpenseNfcMetadataRepository {
}
