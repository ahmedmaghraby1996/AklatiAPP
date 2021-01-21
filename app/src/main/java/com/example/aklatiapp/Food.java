
package com.example.aklatiapp;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Food implements Serializable
{
  private String description;
  private String photo;
  private Integer price;
  private String type;
  private Date updated;
  private String cookmail;
  private String objectId;
  private String ownerId;
  private String cookphone;
  private Date created;
  private String title;
  private String cookname;
  private String time;
  public String getDescription()
  {
    return description;
  }

  public void setDescription( String description )
  {
    this.description = description;
  }

  public String getPhoto()
  {
    return photo;
  }

  public void setPhoto( String photo )
  {
    this.photo = photo;
  }

  public Integer getPrice()
  {
    return price;
  }

  public void setPrice( Integer price )
  {
    this.price = price;
  }

  public String getType()
  {
    return type;
  }

  public void setType( String type )
  {
    this.type = type;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getCookmail()
  {
    return cookmail;
  }

  public void setCookmail( String cookmail )
  {
    this.cookmail = cookmail;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getCookphone()
  {
    return cookphone;
  }

  public void setCookphone( String cookphone )
  {
    this.cookphone = cookphone;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public String getCookname()
  {
    return cookname;
  }

  public void setCookname( String cookname )
  {
    this.cookname = cookname;
  }

  public String getTime()
  {
    return time;
  }

  public void setTime( String time )
  {
    this.time = time;
  }

                                                    
  public Food save()
  {
    return Backendless.Data.of( Food.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Food> callback )
  {
    Backendless.Data.of( Food.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Food.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Food.class ).remove( this, callback );
  }

  public static Food findById( String id )
  {
    return Backendless.Data.of( Food.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Food> callback )
  {
    Backendless.Data.of( Food.class ).findById( id, callback );
  }

  public static Food findFirst()
  {
    return Backendless.Data.of( Food.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Food> callback )
  {
    Backendless.Data.of( Food.class ).findFirst( callback );
  }

  public static Food findLast()
  {
    return Backendless.Data.of( Food.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Food> callback )
  {
    Backendless.Data.of( Food.class ).findLast( callback );
  }

  public static List<Food> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Food.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Food>> callback )
  {
    Backendless.Data.of( Food.class ).find( queryBuilder, callback );
  }
}