* Add Event Repository
* Update Aggregate to accept commands and return events or error


## Design problems:
The aggregate pattern needs some re-thinking; how much command
handling should occur within it? Should an instance of Aggregate
have dependencies on other data sources? e.g. make calls to
databases? If so, should there be a Factory to ease the packaging?
If not, well, what's the difference between a Service layer and an Aggregate?

Perhaps Aggregates just know how to load their current state (including)
attached children



