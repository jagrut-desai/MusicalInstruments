package com.jdcompany.musicalinstruments;

/**
 * Created by Malware on 11/1/2016.
 */

public class MusicalInstrument {

    String name;
    String type;
    String id;
    String imageUrl;

    MusicalInstrument(String name, String type, String id, String imageUrl)
    {
        this.name = name;
        this.type = type;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }


}
