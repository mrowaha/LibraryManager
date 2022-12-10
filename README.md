# LibraryManager
______________________

## LibraryManager Database Setup
__________________________________
Go to /LibraryManager/LibraryManager.api/api/src/main/resources/application.properties

Update postgresql username and password

## LibraryManager API Endpoints
____________________________________

The api uses basic form of API Key authentication as the current security implementation
```
1. GET all authors or by name or genre
example fetch URL 
http://localhost:8080/api/author
http://localhost:8080/api/author?name=Rowaha
http://localhost:8080/api/author?genre=Computer%20Science
http://localhost:8080/api/author?name=Rowaha&genre=Computer%20Science (name has priorty over genre)
```

```
2. POST new author
example post 
http://localhost:8080/api/author
JSON Body 
{
  "name" : "Rowaha",
  "description" : "content writer",
  "genres" : [ "Computer Science" ]
}
```

```
3. GET author by id
example fetch
http://localhost:8080/api/author/1
```

```
4: PUT edit an author
example update
http://localhost:8080/api/author/1

JSON body 
{
  "name" : "changed Rowaha"
}
```

```
5. DELETE author by id
example delete
http://localhost:8080/api/author/1
```

```
6. GET all authors or by name or genre
example fetch URL 
http://localhost:8080/api/book
http://localhost:8080/api/book?name=Java
http://localhost:8080/api/book?genre=Computer%20Science
http://localhost:8080/api/book?name=Java&genre=Computer%20Science (name has priorty over genre)
```

```
7. POST book by author id
example post
http://localhost:8080/api/author/2/book

JSON Body
{
    "name" : "Python and Javascript for beginnners",
    "description" : "A guide to Python/Django and React",
    "genres" : [
        "Computer Science"
    ]
}
```

```
8. GET book by id
example fetch
http://localhost:8080/api/book/1
```

```
9. GET books by author id
exmaple fetch
http://localhost:8080/api/author/1/book
```

```
10. PUT edit book
example edit 
http://localhost:8080/api/book/1
JSON Body
{
  "author" : {
      "id" : 3
  }
}
```

```
11. DELETE book
example delete
http://localhost:8080/api/book/1
```
The api supports creation of user and issuing of books but the frontend has not yet implemented those endpoints
these endpoints include
```
POST new user
GET user by email and password
PUT issue book to user
GET books issued to user
GET unissue book
DELETE user
```
