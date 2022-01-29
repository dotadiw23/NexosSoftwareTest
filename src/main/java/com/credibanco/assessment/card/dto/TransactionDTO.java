package com.credibanco.assessment.card.dto;

public class TransactionDTO {

    public static String STATUS_APPROVED = "Approved";
    public static String STATUS_REJECTED = "Rejected";
    public static String STATUS_CANCELLED = "Cancelled";

    private String responseCode;
    private String message;
    private String transactionStatus;
    private String referenceNumber;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
