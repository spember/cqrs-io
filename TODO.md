* Add Event Repository
* Update Aggregate to accept commands and return events or error


## Design problems:
The aggregate pattern needs some re-thinking; how much command
handling should occur within it? Should an instance of Aggregate
have dependencies on other data sources? e.g. make calls to
databases? If so, should there be a Factory to ease the packaging?
If not, well, what's the difference between a Service layer and an Aggregate?

Perhaps Aggregates just know how to load their current state (including)
attached children.

New thoughts... Aggregates:

* are just entities that are important or first-class
* may have other entities, but those must not be able to live by themselves 
* know how to load their (and children) states
* have meaningful method names, like commands (e.g. addBook). Do NOT ACCEPT COMMANDs
* methods ensure that if an Aggregate changes, its state is always 'good'
* methods return events
* if a dependency is needed (e.g. another repository or a specific other Agg), it is 
 passed as an argument
* command handling logic goes in a service




