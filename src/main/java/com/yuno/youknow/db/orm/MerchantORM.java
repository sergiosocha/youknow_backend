package com.yuno.youknow.db.orm;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "merchant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantORM {

    @Id
    @Column(name = "merchant_id", nullable = false)
    private String merchantId;



}
