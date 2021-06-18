# CQRS.io

A small library meant to help construct [Event Sourced](https://martinfowler.com/eaaDev/EventSourcing.html) and 
[CQRS (Command Query Responsibility Segregation)](https://martinfowler.com/bliki/CQRS.html#:~:text=CQRS%20stands%20for%20Command%20Query,you%20use%20to%20read%20information.)
 applications.
 
## Project Goals

* Low level primitives, mostly.
* Building blocks, a library; definitely ___not___ a framework.
* Core module is un-opinionated, with additional modules to provide more structure (e.g. here's how it should with SQL, or rxJava).
* Reduce complexity in future applications by removing as much of the boilerplate as possible.

## Terminology

Please refer to the above links and the classic [Domain Driven Design](https://www.dddcommunity.org/book/evans_2003/) 
book for _far_ more accurate descriptions of these terms. 

## General Guidelines and Rules



## ... but using them actually in code

The previous descriptions may not align 100% with the 
literature, but after building these types of systems for years now, this is how I've come to describe the concepts
successfully to others - and implement applications that follow the official spirit. 

In other words, here's some general principles for how to structure the CQRS/DDD rules as code, which may not be 
entirely accurate, but that I've found to work well.





