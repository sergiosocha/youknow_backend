package com.yuno.youknow.event;


import com.yuno.youknow.db.orm.EventoPago;

public record TransactionCreatedEvent(EventoPago transaction) {}