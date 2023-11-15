package com.proposalmanager.credit;

import com.proposalmanager.domain.enums.CreditKind;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditDto {
    private Long id;
    private Long userId;
    private Long accountId;
    private String proposalNumber;
    @Enumerated(EnumType.STRING)
    private CreditKind creditKind;
}
