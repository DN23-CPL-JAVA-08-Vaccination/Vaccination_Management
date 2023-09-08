package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {
}
