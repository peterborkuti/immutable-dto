[Old app] uses DTOs, aka Data Transer Objects between layers. DTOs are for carrying data wihout changes between different parts of code. Using DTOs is a good practice and it is used frequently in the IT Industry.

However, in [old app] the code of DTOs is questionable.

requirements for DTOs:

Because DTOs are for carrying data without changing, I think the most important things:

The data can not be changed. Nor by the DTO itself, nor yb the outside word.
Why? Because if data in DTOs can be changed, anything can happen. Hackers can tampering with data, program bugs can change data.
It can lead to not clear code because coders should always check if data in DTOs are valid or even exists.
The data in the DTOs should always be valid.
Why? Because there is no point of carrying invalid data. We should fail fast.
The DTOs should not have any business logic, apart from the initial validation.
Why? Because DTOs data can not be changed, so the business logic in the DTOs will process every time the same data, so the outcome should be the same. So instead of storing the business logic, DTOs can store the outcome.
Also, the business logic for an application should be in one place and not spreading accross the application. It is because of we need testing business logic well and the business logic should be independent of the application.

From these requirements, here is how we should code DTOs:

DTOs should be immutable
DTOs should have validation logic in its constructor. If The data are invalid, DTO should not be created.
DTOs must not have any method, only getters and constructor. Maybe some private helper for validation.
Proposal for DTOs

```java
@Getter
public class CarDto {
    private final long id;
    private final String color;
    private final Set<Long> ownerIds;

    public CarDto(Long id, String color, Set<Long> ownerIds) {
        // validate(...)
        this.id = id;
        this.color = color;
        this.ownerIds = ImmutableSet.copyOf(ownerIds);
        validate();
    }

    private void validate() {
        //if ( ... ) throw some Exception
    }
}
```



It usesonly getters, private final fields, a constructor with validation. It is a matter of taste if we are validating first or after setting the fields. The validatior should throw an exception to prevent creating invalid objects.

I used Guava to create immutable objects. Java 8's Collections.unmodifiableXXX() is not good, because if the backing collection changes, the unmodifiable collection also will change.

This DTO works with Spring Boot controllers as a returning object, like this:

```java

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService service;

    @GetMapping(name = "cars")
    public List<CarDto> cars(){
        return service.cars();
    }
}
```



Advantages of immutable objects (ChatGPT)
1. Thread Safety

Immutable objects are inherently thread-safe because their state cannot be modified after creation. This eliminates the need for synchronization mechanisms, making it easier to write and maintain concurrent applications.

2. Simplicity and Predictability

Immutable objects are simpler to understand and reason about since their state does not change. This makes the code more predictable and easier to debug.

3. Integrity and Consistency

Since the state of immutable objects cannot be altered once created, they help maintain data integrity and consistency. Once you create an immutable DTO, you can be sure that its state will not change, reducing the risk of bugs caused by unexpected mutations.

4. Ease of Testing

Immutable objects are easier to test because they do not change state. This allows for simpler unit tests and makes it easier to use them as fixtures in tests.

5. Safe Sharing

Immutable objects can be freely shared across different parts of the application without worrying about unintended side effects. This is particularly useful in distributed systems where DTOs are passed between different services or components.

6. Functional Programming

Immutable objects align well with functional programming paradigms, which emphasize the use of immutable data structures. This can lead to cleaner and more maintainable code when combined with functional programming techniques.

7. Caching and Hashing

Immutable objects can be safely used as keys in hash-based collections (like hash maps or hash sets) since their hash codes do not change over time. This is beneficial for caching mechanisms and can improve the performance of collections.

8. Less Defensive Copying

With mutable objects, defensive copying is often necessary to prevent unintended modifications. Immutable objects eliminate the need for such defensive copying, reducing boilerplate code and improving performance.

9. Clear Intent

Using immutable objects for DTOs makes it clear to other developers that these objects are intended to be used for data transfer and not for manipulation. This can improve the readability and maintainability of the codebase.

10. Security

Immutable objects can enhance security by preventing unauthorized or accidental changes to data. This is particularly important in systems where data integrity is crucial.




Disadvantages of Immutable DTOs (ChatGPT)




1. Increased Boilerplate Code

Creating immutable objects often requires more boilerplate code compared to mutable objects. This can include defining constructors, getters, and potentially builder patterns to make object creation more flexible.

2. Limited Flexibility

Once an immutable object is created, its state cannot be changed. This can be limiting in scenarios where object mutation is necessary or beneficial, requiring the creation of new instances for every change, which can be less efficient.

3. Performance Overhead

Creating new instances of immutable objects instead of modifying existing ones can lead to increased memory usage and garbage collection overhead. In performance-critical applications, this can become a significant concern.

4. Complex Object Construction

Constructing complex immutable objects can be cumbersome, especially when they have many fields. This often necessitates the use of builder patterns, which can add complexity and additional boilerplate.

5. Compatibility Issues

Some libraries and frameworks expect mutable objects and may not work correctly with immutable ones. This can require additional adaptation code or even preclude the use of certain tools and libraries.

6. Serialization and Deserialization Overhead

Immutable objects often need to be serialized and deserialized, especially in distributed systems or for persistence. This can introduce additional overhead and complexity, particularly when dealing with frameworks that do not natively support immutable objects.

7. Constructor Explosion

When dealing with objects that have many fields, constructors can become unwieldy. Providing constructors that handle various combinations of fields can lead to a "constructor explosion," making the code harder to maintain.

8. Unit Testing with Mocking

While immutable objects are generally easier to test due to their predictability, they can be harder to mock in tests compared to mutable objects. This can complicate testing strategies, particularly in unit tests where you might want to mock or stub certain behaviors.

9. Refactoring Challenges

Refactoring code that uses immutable objects can be more challenging. Adding new fields or changing the constructor signature requires changing all instances where the object is created, which can be cumbersome in large codebases.

10. Immutability Constraints

Immutability constraints can sometimes be overly restrictive, requiring workarounds that complicate the code. For example, when implementing certain design patterns or handling special cases, immutability can force less intuitive or less efficient solutions.