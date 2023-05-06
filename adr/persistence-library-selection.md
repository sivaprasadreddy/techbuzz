# Tech selection for database persistence

## Question: Which database persistence library to use?

## Options:
1. Spring Data JPA with Hibernate
2. jOOQ
3. Spring JdbcTemplate

## Decision
Use jOOQ

## Reasons
* JPA and Spring Data JPA libraries are very powerful but need good expertise to use them properly
* There is a good chance to misuse JPA, especially while doing anything beyond simple CRUD
* jOOQ provides TypeSafe DSL to perform database operations
* jOOQ doesn't do any magic behind the scenes. jOOQ will exactly execute the query that you tell it to execute.
* With MULTISET feature, loading *-to-Many associations are also easy

## Consequences
* With jOOQ it may take a little bit more time to write code compared to JPA, but you won't lose hair debugging JPA issues later :-)
* There could possibly be more effort to switch to a different database compared to JPA
