package com.code.testcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
public class CompositeId {
    public final int systemKey;
    public final String systemEntityId;

    public CompositeId(int systemKey, String systemEntityId) {
       // Preconditions.checkNotNull(systemEntityId, "systemEntityId may not be null");
        this.systemKey = systemKey;
        this.systemEntityId = systemEntityId;
    }

    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            CompositeId that = (CompositeId)obj;
            return Objects.equals(this.systemKey, that.systemKey) && Objects.equals(this.systemEntityId, that.systemEntityId);
        } else {
            return false;
        }
    }

    public String getSystemEntityId() {
        return this.systemEntityId;
    }

    public int getSystemKey() {
        return this.systemKey;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.systemKey, this.systemEntityId});
    }

    public String toString() {
        return "CompositeId [systemKey=" + this.systemKey + ", systemEntityId=" + this.systemEntityId + "]";
    }

}
