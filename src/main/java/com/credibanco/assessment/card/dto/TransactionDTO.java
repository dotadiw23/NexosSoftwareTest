package com.credibanco.assessment.card.dto;

import java.util.Date;

public class TransactionDTO {

    public TransactionDTO() {

    }
    public TransactionDTO(Date transactionDate, String referenceNumber, String transactionStatus, Double total) {
        this.transactionDate = transactionDate;
        this.referenceNumber = referenceNumber;
        this.transactionStatus = transactionStatus;
        this.total = total;
    }

    public static String STATUS_APPROVED = "Approved";
    public static String STATUS_REJECTED = "Rejected";
    public static String STATUS_CANCELLED = "Cancelled";

    private String responseCode;
    private String message;
    private String transactionStatus;
    private String referenceNumber;
    private Double total;
    private Date transactionDate;
    private String address;

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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
