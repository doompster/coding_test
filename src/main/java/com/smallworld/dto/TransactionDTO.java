package com.smallworld.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author avaldez
 */
@Getter
@Setter
public final class TransactionDTO {
    private int mtn;
    private double amount;
    private String senderFullName;
    private int senderAge;
    private String beneficiaryFullName;
    private int beneficiaryAge;
    private int issueId;
    private boolean issueSolved;
    private String issueMessage;
}