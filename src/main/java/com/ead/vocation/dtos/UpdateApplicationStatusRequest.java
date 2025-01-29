package com.ead.vocation.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationStatusRequest {
    @Pattern(regexp = "PENDING|REJECTED|ACCEPTED", message = "Invalid status")
    private String newStatus;
}
