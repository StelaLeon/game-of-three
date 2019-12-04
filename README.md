#### Setup:   
    Please change your ip-address in docker-compose.yml :
    KAFKA_ADVERTISED_HOST_NAME: <your ip address>
    docker-compose up -> to start the 2 containers with a kafka q container
    docker-compose down -> should tear down the cluster
    You can always connect on (obviously if you don;t run it on localhost, then set up the ip of the place you;re running it) localhost:8080/status and localhost:8081/status from your browser to check the status of your game, you will receive a list of player's moves(basically the whole game log)
    
    
## Description
The motivation behind is to implement a simple game of three on top of the jvm. 

#### High priority improvements:
    1.
    Better test coverage: there are already 2 commented IT waiting to be finished as a second iteration of this task.
    2. 
    Re-try mechanism in case of equal random number(a bad random enthropy is based on time, as a result it could be possible to have the same random number generated, although unprobable).
    3.
    Design the GameEngine class into: a tree of States interpreted by a driver class: in order to avoid state transition we can only follow the tree to follow the possibility: a more functional programming way of doing it, the limitation is the control to the lifecycle of the bean. 
    
#### Possible improvements that would change the nature of the game:
    1.
    For local testing my intention was to develop a small simple plugins in order to run your integration tests in 1 click:
    syncronization of tasks {e.g. first you pull up the microsystem of services then you run your it against it} 
    Disadvantage: container synscronization from within the dependency manager=> not in production 
    as you don't have the flexibility and container awareness: choose Kube, and we should separate the infra 
    from the domain 
    
    2.
    Would be nice if services connect to a particular topic and listen for players waiting to play. 
    For this we need a service discovery mechanism:
        -> containers with different group ids that would treat kafka as a broadcast source:
        the algorythm making assumptions on kafka's configuration is a no-no.
        -> service discovery by different types of containers at start up: no no
        -> different group ids: heavy in terms of consuming messages from the beginning of hte world: not the case
            and a lot of assumptions related to the settings of kafka (not for interactive apps)
        -> more consistent to the type and the nature of the project: peer to peer over kafka 
            with starting a topic and destroying it at the end of the lifecycle.

#### Why we building it and how -> Random thoughts 
the motivation behind it might compensate for lack of requirements.
###### Scenario1 
we build a game engine so what one connects and play against it 
which means different code base and 2 different services
I would have choosen an actor based solution. 

###### Scenario2:
We build individual services that compete against each other with descentralised system.
This is simpler but pretty heavy weight: every time one wants to play a game a new container is needed:
all boils down to how loaded your service is. 

I choose Scenario2, as per requirements.

### Why the tech-stack:

SBT motivation: as a more compact way, better possibilities and better usability that maven,
yet longer startups with eventual better parallelism of the tasks defined from that point on.
        
docker-compose: easy to work with for local and quick local testing, not a production tool=> try kube

spring: for this kind of tasks I would have preffered an actor based framework for the benefits of spinning a large amount of actors  easy to spin up and destroy when a demand for a game is received, also for a more flexible way to control its lifecycle, 
I wanted to dust-off my Spring knowledge and (from my experience) it is faster than Akka so for the nature of the problem: spin 1 container for each player, it is a benefit. 

## Documentation
For sbt-java specific config. 
    https://www.scala-sbt.org/1.x/docs/Java-Sources.html
