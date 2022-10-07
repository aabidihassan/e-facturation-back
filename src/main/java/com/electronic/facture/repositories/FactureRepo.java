package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Facture;

@Repository
public interface FactureRepo extends JpaRepository<Facture, Long>{

}
