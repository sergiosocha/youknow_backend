package com.yuno.youknow.db.repository;

import com.yuno.youknow.db.orm.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, String> {}
