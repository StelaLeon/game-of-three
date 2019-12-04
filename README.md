## Description
The motivation behind is to implement a simple game of three on top of the jvm. 
From technical perspective this will be run as as many containers one wishes. 

why we building it 
the motivation behind it might compensate for lack of requirements.
Scenario1 
we build a game engine to what you connect and user can play against it 
which means different code base and 2 different services
I would have choosen an actor based solution. 

Scenario2:
We build individual services that compete against each other with descentralised system
simpler but pretty heavy weight: every time one wants to play a game a new container is needed:
all boils down to how loaded your service is. 

I choose Scenario2,

SBT motivation: as a more compact way, better possibilities and better ussability that maven,
yet longer startups with eventual better parallelism of the tasks defined.
Possible improvement:
    For local testing developing a small simple plugins in order to run your integration tests in 1 click:
    syncronization of tasks {e.g. first you pull up the microsystem of services then you run your it against it} 
    Disadvantage: container synscronization from within the dependency manager=> not in production 
    as you don't have the flexibility and container awareness: choose Kube, and we should separate the infra 
    from the domain 
    
Why docker-compose: easy to work with for local and quick local testing, not a production tool=> try kube

Service discovery -> containers with different group ids that would treat kafka as a broadcast source:
    assuming the algorythm coupled with the configuration of kafka, it should be idempotent.
    -> service discovery by different types of containers at start up: no no
    -> different group ids: heavy in terms of consuming messages from the beginning of hte world: not the case
        and a lot of assumptions related to the settings of kafka (not for interactive apps)
    -> more consistent to the type and the nature of the project: peer to peer over kafka 
        with starting a topic and destroying it at the end of the lifecycle.
    
  
## Documentation
For sbt-java specific config. 
    https://www.scala-sbt.org/1.x/docs/Java-Sources.html
