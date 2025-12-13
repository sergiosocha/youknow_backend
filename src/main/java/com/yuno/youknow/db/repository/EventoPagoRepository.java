package com.yuno.youknow.db.repository;

import com.yuno.youknow.db.orm.EventoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoPagoRepository extends JpaRepository<EventoPago, String> {

    List<EventoPago> findByTimestampBetween(LocalDateTime from, LocalDateTime to);


}

