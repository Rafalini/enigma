# Recrutation task

There is merge request to main branch, to make it easier to comment and contend changes.

## Assumptions for task 1:
* only symbolic functionalities implemented (like formula for photosynthesis)

## Assumptions for task 2
* many users can be assigned to single task
* many tasks can be assigned to single user
* user cannot be deleted if he is assigned to task
* task can be deleted if it's assigned to any amount of users
* app is using H2 in-memory database - each shutdown clears database

There is postman collection of requests in
```/task2/enigmabackend/enigma.postman_collection.json```