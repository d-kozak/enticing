# Conventions 
Conventions that should be used consistently in the source code.
## React - Redux
* Props for container components should be inferred from mapStateToProps & mapDispatchToProps. Note that actions returning ThunkResult<> have to cast to return void. There might be a solution for this problem, but unfortunately I am not aware of it now. 
## Redux
### Actions
* Action types should be defined as string constants, which will then be used everywhere else in the code instead of a string literal. This allows better refactoring.
* Each action should have a suffix Action in it's name.
* Files containing redux actions should be named in plural(smthActions.ts), but the exported type should have name in singular(SmthAction).
### Reducers
* Each reducer should have a suffix Reducer in it's name.
* Each reducer is responsible for defining it's part of state. The state should be inferred from the reducer's initialState if possible.
* The state for combined reducers can be infered using StateFromReducers\<T\> mapped type. This way the whole AppState can be inferred from the root combinedReducers.
  