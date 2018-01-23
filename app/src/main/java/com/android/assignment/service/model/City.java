package com.android.assignment.service.model;

import java.util.List;

public class City{
    private List<Objects> objects;

    private Meta meta;

    public List<Objects> getObjects ()
    {
        return objects;
    }

    public void setObjects (List<Objects> objects)
    {
        this.objects = objects;
    }

    public Meta getMeta ()
    {
        return meta;
    }

    public void setMeta (Meta meta)
    {
        this.meta = meta;
    }

    @Override
    public String toString()
    {
        return "Objects [objects = "+objects+", meta = "+meta+"]";
    }
}
