# Conventions 
Conventions that should be used consistently in the source code.
## Redux
### Actions
* Action types should be defined as string constants, which will then be used everywhere else in the code instead of a string literal. This allows better refactoring.
* Files containing redux actions should be named in plural(smthActions.ts), but the exported type should have name in singular(SmthAction).
  