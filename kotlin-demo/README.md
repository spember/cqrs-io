# Module kotlin-demo
## Kotlin Demo - Library Management

To test this library we'll make a demo for a Library book tracking.

# Package io.cqrs.kt.bookstore.core

Normally, I would break out the core functionality of a service or application into a `core` module,
with the goal of having as few dependencies as possible, fully tested by unit tests. 
`Core` exposes exteneral dependencies & functionality (e.g. database or third party calls) as Repository interfaces,
and incoming requests as Command and Query Handlers.

We then create additional modules to implement our interfaces for the particular technology (e.g. postgres, aws, etc).

Some main or application module is then created to 'pull it all together' and is generally the location of
some framework code. In this case, we choose to use [Micronaut](https://micronaut.io/).

# Package io.cqrs.kt.bookstore.dto

Data Transfer Objects representing the data structures we wish to expose to the 'world'. Depends on objects within `core`,
and core must never depend on these. May seem like additional and unnecessary mapping code, but allows us to control 
the representations of our `core` data model and not expose certain things to the outside world. Specifically, you usually
don't to allow the full data in your entities / aggregates to bleed out.

Furthermore, this is an ideal place for Parameters,response objects, message structures, etc that are of no concern to 
`core`.
