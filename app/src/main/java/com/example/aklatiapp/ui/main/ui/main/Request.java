
package com.example.aklatiapp.ui.main.ui.main;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class Request
{
  private String title;
  private String ownerId;
  private String pic;
  private Date updated;
  private String phone;
  private String aproval;
  private String name;
  private String ftime;
  private String cookphone;
  private String cookmail;
  private String cookname;
  private String usermail;
  private String number;
  private String objectId;
  private Date created;
  private String content;
  private String note;
  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getPic()
  {
    return pic;
  }

  public void setPic( String pic )
  {
    this.pic = pic;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone( String phone )
  {
    this.phone = phone;
  }

  public String getAproval()
  {
    return aproval;
  }

  public void setAproval( String aproval )
  {
    this.aproval = aproval;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getFtime()
  {
    return ftime;
  }

  public void setFtime( String ftime )
  {
    this.ftime = ftime;
  }

  public String getCookphone()
  {
    return cookphone;
  }

  public void setCookphone( String cookphone )
  {
    this.cookphone = cookphone;
  }

  public String getCookmail()
  {
    return cookmail;
  }

  public void setCookmail( String cookmail )
  {
    this.cookmail = cookmail;
  }

  public String getCookname()
  {
    return cookname;
  }

  public void setCookname( String cookname )
  {
    this.cookname = cookname;
  }

  public String getUsermail()
  {
    return usermail;
  }

  public void setUsermail( String usermail )
  {
    this.usermail = usermail;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber( String number )
  {
    this.number = number;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getContent()
  {
    return content;
  }

  public void setContent( String content )
  {
    this.content = content;
  }

  public String getNote()
  {
    return note;
  }

  public void setNote( String note )
  {
    this.note = note;
  }

                                                    
  public Request save()
  {
    return Backendless.Data.of( Request.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Request> callback )
  {
    Backendless.Data.of( Request.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Request.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Request.class ).remove( this, callback );
  }

  public static Request findById( String id )
  {
    return Backendless.Data.of( Request.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Request> callback )
  {
    Backendless.Data.of( Request.class ).findById( id, callback );
  }

  public static Request findFirst()
  {
    return Backendless.Data.of( Request.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Request> callback )
  {
    Backendless.Data.of( Request.class ).findFirst( callback );
  }

  public static Request findLast()
  {
    return Backendless.Data.of( Request.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Request> callback )
  {
    Backendless.Data.of( Request.class ).findLast( callback );
  }

  public static List<Request> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Request.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Request>> callback )
  {
    Backendless.Data.of( Request.class ).find( queryBuilder, callback );
  }
}