package com.credibanco.assessment.card.dto;

public class CardDTO<TYPE_CREDIT> {

    public static String TYPE_CREDIT = "credit";
    public static String TYPE_DEBIT = "debit";
    public static  String TYPE_CREATED = "created";
    public static  String TYPE_ROLlED = "rolled";

    private String responseCode;
    private String message;
    private String validationCode;
    private String cardNumber;
    private String systemId;
    private String owner;
    private String documentId;
    private String status;

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

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOwner() {
        return owner;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
