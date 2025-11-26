package com.example.neonapp.dto;

public class DeclineNoDueRequestDto {

    private Long id;
    private String reason;
    private String declinerName; // optional

    public DeclineNoDueRequestDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDeclinerName() { return declinerName; }
    public void setDeclinerName(String declinerName) { this.declinerName = declinerName; }
}
