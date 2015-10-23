package ru.home.miniplanner.domain;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import ru.home.miniplanner.service.factory.SQLiteMapper;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class Domain implements Serializable {

    @DatabaseField(id = true, generatedId = true)
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
