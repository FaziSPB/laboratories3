package pt.training.maven.jee.model;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Disposes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class WidgetRepositoryProducer
{
   @PersistenceContext EntityManager em;

   public @Produces @WidgetRepository EntityManager retrieveEntityManager() {
      return em;
   }

   public void disposeEntityManager(@Disposes @WidgetRepository EntityManager em) {}
}
