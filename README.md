# ArangoDB Spring APIs - CharacterController

This repository contains a Java Spring project demonstrating the implementation of CRUD operations using ArangoDB in the CharacterController.

## Features

- **List Characters**: Retrieve a list of all characters.
- **Create Character**: Add a new character to the database.
- **Update Character**: Update an existing character's information.
- **Delete Character**: Remove a character from the database.

## Prerequisites

Before running this project, ensure you have the following installed:

- Java Development Kit (JDK)
- IntelliJ IDEA (or any preferred Java IDE)
- Docker

## Setup

### Importing the Project

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/your-repository.git
   
2. **Open Project in IntelliJ IDEA:**
   -Launch IntelliJ IDEA.
   -Go to File > Open and select the root directory of the cloned project.

### Setting up ArangoDB using Docker

1. **Run ArangoDB Container:**
   ```bash
   docker run -e ARANGO_NO_AUTH=1 -p 8529:8529 -d --name test-arangodb arangodb
   
2. **Verify ArangoDB Container:**
- Check if the container is running:
    ```bash
    docker ps
    
- Access ArangoDB Web Interface:
    Open a web browser and go to http://localhost:8529.

## Running the Application

### Start the Spring Application:

To run the application, follow these steps:

1. Open the project in IntelliJ IDEA.
2. Locate the `CharacterController` class in the project.
3. Right-click on the `CharacterController` class.
4. Select "Run CharacterController" or "Debug CharacterController" from the context menu.

### Test the APIs:

After running the application, you can test the APIs using tools like Postman or cURL. Here are the available endpoints:

- **GET /api/character**: Retrieve all characters.
- **POST /api/character**: Add a new character.
- **PUT /api/character/{id}**: Update a character by ID.
- **DELETE /api/character/{id}**: Delete a character by ID.

Use appropriate HTTP methods (GET, POST, PUT, DELETE) along with the provided endpoints to interact with the API endpoints. For instance:

- Use a GET request to `/api/character` to retrieve all characters.
- Use a POST request to `/api/character` to add a new character.
- Use a PUT request to `/api/character/{id}` to update a character by specifying its ID.
- Use a DELETE request to `/api/character/{id}` to delete a character by specifying its ID.

Ensure you provide valid data and appropriate request payloads while testing the APIs.

   
