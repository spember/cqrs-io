Ok new plan:

* update readme to contain basic flow between layers
* aggregate functions are expected to mutate themselves and return the state changes.
* aggregate functions are expected to *not* persist those events on their own 
* it is not a hard and fast rule, but generally encouraged
* as such, Aggregates should be passed the commands as instructions for mutation, and the AggregateMutationResult 
maintains underlying EntityWithEvents objects, which in turn apply the event to the entity

# wait, now that we need to serialize / deserialize, we need a 'registry' 
by marking the event with the class it belongs to, we can get the types we need
to 'cache' on startup.

Registry:

maintains a mapping of Event class to... waaait
on hydration:
* serialize event data into class
* create event envelope from the ID THAT WAS SENT TO IT AHHH

