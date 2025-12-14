package com.yuno.youknow.db.repository;

import com.yuno.youknow.controller.dto.AlertaDTO;
import com.yuno.youknow.db.orm.Alerta;
import com.yuno.youknow.db.orm.EventoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, String> {


}
