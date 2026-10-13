package pt.training.maven.jee.web;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/members")
@RequestScoped
public class MemberResourceRESTService
{
   @GET
   @Produces("text/plain")
   public String ping()
   {
      return "Hello World";
   }
}
