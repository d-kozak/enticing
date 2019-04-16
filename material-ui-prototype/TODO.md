# Todo list
- [X] Login page
- [X] SignUp page
- [ ] Settings page
    - [X] Change settings(logged in user)
    - [X] Choose default settings(when not logged in)
    - [ ] Edit default settings (ADMIN, see ADMIN mode) 
- [X] MainPage
- [ ] SearchResult page
    - [X] show results
    - [X] enable submitting a query from this page as well
    - [X] show annotations
    - [ ] find a cleaner way to handle annotations, current implementation is messy
    - [ ] encode query in url (so that the search can be started )
    
- [ ] Add some intelligence to the query input component
    -[X] react-codemirror with simple lexing
    -[ ] more effective lexer
    -[ ] entity types recognition
    -[ ] autocomplete based on entity type
    -[ ] complex support using parser in the backend
- [X] Admin mode
    - [X] add admin related buttons where necessary
- [ ] Code cleanup
    - [ ] react-redux: create clean separation between components and containers    
    - [ ] ensure consistent file naming, e.g. action vs actions
    - [ ] use string constants instead of strings in actions types(better refactoring)
- [ ] Add annotations, they were temporarily removed, add them back 
## Features
- [ ] current layout centering is not very responsive, use flex or Grid instead 
- [ ] show the 'Query:' text only on large screens
- [X] add redux
- [ ] inspect the possibility to make all progress and spinners depend on one top level element
- [ ] make mock API fully asynchronous
- [ ] handle error cases when interacting with mock API 


## Known bugs
- [ ] options for logged in user from appbar sometimes render in the wrong place
- [ ] tooltips sometimes mysteriously render push the text after them to the next line