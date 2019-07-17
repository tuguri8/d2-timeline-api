package com.timeline.api.domain.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.isNull;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity {
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDateTime;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", updatable = true)
    private Date lastModifiedDateTime;

    public LocalDateTime getCreatedDateTime() {
        return getLocalDateTimeFrom(createdDateTime);
    }

    public void setCreatedDateTime(final LocalDateTime createdDateTime) {
        this.createdDateTime = getDateFrom(createdDateTime);
    }

    public LocalDateTime getLastModifiedDateTime() {
        return getLocalDateTimeFrom(lastModifiedDateTime);
    }

    public void setLastModifiedDateTime(final LocalDateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = getDateFrom(lastModifiedDateTime);
    }

    private LocalDateTime getLocalDateTimeFrom(Date date) {
        return isNull(date) ? ofInstant(ofEpochMilli(new Date().getTime()), systemDefault()) : ofInstant(ofEpochMilli(date.getTime()),
                                                                                                         systemDefault());
    }

    private Date getDateFrom(LocalDateTime localDateTime) {
        return isNull(localDateTime) ? Date.from(LocalDateTime.now().atZone(systemDefault())
                                                              .toInstant()) : Date.from(localDateTime.atZone(systemDefault())
                                                                                                     .toInstant());
    }
}
