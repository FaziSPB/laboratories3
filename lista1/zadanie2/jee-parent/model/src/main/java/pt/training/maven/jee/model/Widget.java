package pt.training.maven.jee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Widget implements Serializable
{
   private Long id;
   private String partNumber;
   private String name;
   private String description;

   @Id
   @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   @Column(name = "partno")
   public String getPartNumber()
   {
      return partNumber;
   }

   public void setPartNumber(String partNumber)
   {
      this.partNumber = partNumber;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }
   
   private static final long serialVersionUID = 1L;
}