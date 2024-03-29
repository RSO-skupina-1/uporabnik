package si.fri.rso.uporabnik.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Uporabnik API", version = "v1",
        contact = @Contact(email = "dg6781@fri.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing users."),
        servers = @Server(url = "http://localhost:8083/"))
@ApplicationPath("/v1")
public class UporabnikApplication extends Application {

}
