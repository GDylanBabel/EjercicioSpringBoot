package com.helloworld.babel.repository;

import com.helloworld.babel.model.Transference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransferenceRepository extends JpaRepository<Transference, Long> {

    @Query("SELECT t FROM Transference t WHERE t.senderAccount = ?1")
    public List<Transference> getOperationsByAccount(String accountCode);


}
