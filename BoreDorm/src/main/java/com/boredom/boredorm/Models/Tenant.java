package com.boredom.boredorm.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tenant {
    private final StringProperty roomNumber = new SimpleStringProperty();
    private final StringProperty fullName = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public Tenant(String roomNumber, String fullName, String status) {
        this.roomNumber.set(roomNumber);
        this.fullName.set(fullName);
        this.status.set(status);
    }

    // Explicit standard getters that PropertyValueFactory mandates
    public String getRoomNumber() { return roomNumber.get(); }
    public String getFullName() { return fullName.get(); }
    public String getStatus() { return status.get(); }

    // Explicit JavaFX property accessors
    public StringProperty roomNumberProperty() { return roomNumber; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty statusProperty() { return status; }
}