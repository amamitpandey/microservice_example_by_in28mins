package com.example.exchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepo extends JpaRepository<ExchangeBean, Integer> {
    // like custom sql query : findBy + field 1 + And field 2 not findByFromAndTo1
    ExchangeBean findByFromAndTo(String from, String to);
}
