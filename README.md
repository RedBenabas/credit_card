# Summary:
* Service for managing credit cards.

# API Contract:
* `Create` and `Index` operations are documented in the [contract](doc/contract/openapispec.yaml)

# Assumptions:
- Card number maximum length is 19 inclusive.
- The results are ordered by name ascending.
- Spring Data JPA is used for the persistence layer implementation
- User can have more than 1 credit card
- A credit card is unique per user
- Schema DDL is created using auto-configuration

# Starting application
- Run class CreditCardApplication as a sprintbooted application
- Application is started on http://localhost:8080

# Future enhancements:
- Parameterised support for Pagination and Sorting
- Refactor schema so that in the event a credit card getting lost, a new card with a different number can be reissued to the same user with the same balance carried over.
- Incorporate authentication/authorization as an external dependency
- Implement the front-end using React/Redux