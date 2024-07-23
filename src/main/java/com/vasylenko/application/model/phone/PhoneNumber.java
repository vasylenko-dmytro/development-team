package com.vasylenko.application.model.phone;

import com.google.common.base.MoreObjects;
import org.springframework.util.Assert;

import java.util.Objects;

public class PhoneNumber {
    private String phoneNumber;

    protected PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "phoneNumber cannot be blank");
        this.phoneNumber = phoneNumber;
    }

    public String asString() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("phoneNumber", phoneNumber)
                .toString();
    }
}
