package com.Abdo_Fahmi.Recipe_Bank.exception;

import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorResponseDTO (
        String message,
        Date timeStamp
) { }
