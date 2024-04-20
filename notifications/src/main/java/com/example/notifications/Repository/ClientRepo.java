package com.example.notifications.Repository;

import com.example.notifications.models.BankAccount;
import com.example.notifications.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client,Long> {
}
