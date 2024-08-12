package com.Abdo_Fahmi.Recipe_Bank.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorResponseDTO {
    private String message;
    private Date timeStamp;
}
