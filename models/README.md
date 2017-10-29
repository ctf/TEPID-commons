# Tepid Models

The following submodules contains all of the data bindings to TEPID.

Klasses are generally in the following format:

### [Model]Json

Models used in Jackson. Contains jackson annotations and have mutable variables.
Move variables also have default values

### [Model]

Immutable data variants of the json models. 
This is typically constructed externally, and it has only the values it needs (no database `_id`, `_rev`, etc)