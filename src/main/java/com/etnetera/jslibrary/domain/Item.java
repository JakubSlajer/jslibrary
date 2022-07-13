package com.etnetera.jslibrary.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Item {
    private LocalDateTime created;
    private LocalDateTime updated;

    public Item() {
        this.created = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
