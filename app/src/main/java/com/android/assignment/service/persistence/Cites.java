package com.android.assignment.service.persistence;


import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "cites")
public class Cites {
    @Id
    @NonNull
    private Long  id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "slug")
    private String slug;
    @Generated(hash = 730133517)
    public Cites(@NonNull Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
    @Generated(hash = 1700735057)
    public Cites() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSlug() {
        return this.slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
}
