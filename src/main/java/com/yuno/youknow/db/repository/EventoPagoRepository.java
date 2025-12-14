package com.yuno.youknow.db.repository;

    import com.yuno.youknow.db.orm.EventoPago;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.time.LocalDateTime;
    import java.util.List;

    public interface EventoPagoRepository extends JpaRepository<EventoPago, String> {

        List<EventoPago> findByTimestampBetween(LocalDateTime from, LocalDateTime to);


        interface AlertaRowView {
            String getMerchantId();
            String getMerchantName();
            String getCountryCode();
            String getProvider();
            String getIncidentTag();
            String getCategory();
            LocalDateTime getLastSeen();
        }

        @Query(value = """
        SELECT 
            ep.merchant_id   AS merchantId,
            ep.merchant_name AS merchantName,
            ep.country_code  AS countryCode,
            ep.provider      AS provider,
            ep.incident_tag  AS incidentTag,
            ep.error_category AS category,
            MAX(ep.timestamp) AS lastSeen
        FROM eventos_pago ep
        WHERE ep.timestamp >= :from AND ep.timestamp <= :to
          AND ep.status = 'FAILED'
        GROUP BY
            ep.merchant_id, ep.merchant_name, ep.country_code,
            ep.provider, ep.incident_tag, ep.error_category
        ORDER BY lastSeen DESC
        """, nativeQuery = true)
        List<AlertaRowView> findAlertasBase(@Param("from") LocalDateTime from,
                                            @Param("to") LocalDateTime to);

    }

