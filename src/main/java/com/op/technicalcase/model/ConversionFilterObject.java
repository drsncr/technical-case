package com.op.technicalcase.model;


public class ConversionFilterObject {
    Long id;
    String creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "ConversionFilterObject{" +
                "id=" + id +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
