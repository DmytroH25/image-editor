# ImageEditor

## Description

ImageEditor is a web application for image editing that allows users to upload, edit, and save images. The application is built using Spring Boot and uses PostgreSQL for data storage.

## Installation

### Requirements

- Java 17
- Maven 3.6+
- PostgreSQL 12+

### Installation Steps

1. Clone the repository:
    ```sh
    https://github.com/DmytroH25/image-editor.git
   cd image-editor
    ```

2. Set up the PostgreSQL database:
    ```sql
    CREATE DATABASE image_editor;
    CREATE USER user1 WITH PASSWORD '0880';
    GRANT ALL PRIVILEGES ON DATABASE image_editor TO user1;
    ```

3. Configure the `src/main/resources/application.properties` file according to your database settings.

4. Run the application using Maven:
    ```sh
    mvn spring-boot:run
    ```

## Usage

### Uploading an Image

1. Navigate to the application's homepage.
2. Click the "Upload Image" button.
3. Select an image from your computer and click "Upload".

### Editing an Image

1. After uploading the image, you will see the editing tools.
2. Use the tools to crop, resize, rotate, and perform other operations.
3. Once editing is complete, click "Save".

## Technologies

- **Spring Boot** - framework for building web applications.
- **PostgreSQL** - relational database.
- **Imgscalr** - library for image processing.

## Development

### Dependencies

The main dependencies used in the project include:

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-security`
- `postgresql`
- `jjwt`
- `imgscalr-lib`

### Project Structure

- `src/main/java` - Java source code.
- `src/main/resources` - resources such as configuration files.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Authors

- **Your Name** - *Initial Developer* - [Dmytro Hordun](https://github.com/DmytroH25)

## Contact

If you have any questions or suggestions, please contact me at [dmytrohordun@gmail.com](mailto:dmytrohordun@gmail.com).