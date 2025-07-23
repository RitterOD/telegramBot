package org.maslov.bot.app.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
public abstract class CommonFields {


    @CreatedDate
    @Column(name = "row_created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @LastModifiedDate
    @Column(name = "row_updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
}
