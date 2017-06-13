package com.nascenia.domain.model;

/** Created by mozammal on 6/12/17. */
public class MediaGalleryEntries {

  private int id;

  private String media_type;

  private String file;

  public MediaGalleryEntries() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMedia_type() {
    return media_type;
  }

  public void setMedia_type(String media_type) {
    this.media_type = media_type;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  /*
       "id":1,
               "media_type":"image",
               "label":null,
               "position":1,
               "disabled":false,
               "types":[
               "image",
               "small_image",
               "thumbnail",
               "swatch_image"
               ],
               "file":"\/t\/e\/testbra.jpeg"
  }*/
}
