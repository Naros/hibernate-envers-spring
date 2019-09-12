# Hibernate Envers + Spring 5.1 Demo

This demo is primarily aimed to illustrate the `ManagedBeanRegistry` support that Spring Framework added in version 5.1 to support injecting Spring beans into a Hibernate Envers `RevisionListener` implementation.

_While this demo makes heavy use of `spring-boot` to simplify setup and configuration, it is not required._

To run this demo
```
mvn package
java -jar target/hibernate-envers-spring-1.0.0-SNAPSHOT.jar
```

The majority of the initial output comes specifically from `spring-boot` initializing the Spring environment.  You will eventually see a few lines that look like the following, which means the application started:
```
2019-09-12 11:58:37.550  INFO 16698 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-09-12 11:58:37.553  INFO 16698 --- [           main] c.g.n.h.Application                      : Started Application in 4.738 seconds (JVM running for 5.082)
```

After this point, the `Application` class which implements the `CommandLineRunner` interface will begin executing a set of test scenario steps that include:

1. Insert a new entity, `MyEntity` into the H2 database
2. Outputs logging from the `ExtendedRevisionListener` bean
3. Invokes the `MyService#doSomething()` method and outputs logging reflecting that
4. Outputs all the Hibernate SQL that gets performed

As an example, you should see something like the following:
```
2019-09-12 11:58:37.642  INFO 16698 --- [           main] c.g.n.h.services.TestServiceImpl         : ** Saving entity
Hibernate: 
    call next value for hibernate_sequence
Hibernate: 
    insert 
    into
        my_entity
        (data, id) 
    values
        (?, ?)
2019-09-12 11:58:37.687  INFO 16698 --- [           main] c.g.n.h.domain.ExtendedRevisionListener  : New revision object created, listener called
2019-09-12 11:58:37.687  INFO 16698 --- [           main] c.g.n.h.domain.ExtendedRevisionListener  : MyService was injected successfully
2019-09-12 11:58:37.687  INFO 16698 --- [           main] c.g.n.h.services.MyServiceImpl           : *** doSomething() called successfully
Hibernate: 
    call next value for hibernate_sequence
Hibernate: 
    insert 
    into
        extended_revision_entity
        (timestamp, id) 
    values
        (?, ?)
Hibernate: 
    insert 
    into
        my_entity_aud
        (revtype, data, id, rev) 
    values
        (?, ?, ?, ?)
```

The `RevisionListener` implementation looks like:
```
public class ExtendedRevisionListener implements RevisionListener {

	private static final Logger LOGGER = Logger.getLogger(ExtendedRevisionListener.class);

	@Autowired
	private MyService myService;

	public ExtendedRevisionListener() {
		LOGGER.info("Created revision listener implementation");
	}

	@Override
	public void newRevision(Object o) {
		LOGGER.info("New revision object created, listener called");
		if (myService != null) {
			LOGGER.info("MyService was injected successfully");
			myService.doSomething();
			return;
		}
		else {
			LOGGER.error("MyService was not injected.");
		}
	}
}

```

and that implementation is wired by setting it on a custom `@RevisionEntity` entity mapping:
```
@Entity
@RevisionEntity(value = ExtendedRevisionListener.class)
public class ExtendedRevisionEntity extends DefaultRevisionEntity {
	// just marker entity to point to listener
	// can accomplish the same by explicitly setting up listener in bootstrap properties
}
```