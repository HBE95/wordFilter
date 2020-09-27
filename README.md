# wordFilter
A small application in spring which reads a file, filters some elements from it and writes a new file containing the filtered words

## Build Requirements
- JDK 8+ 
- Maven 3+

## Usage
- Import the project in Eclipse / Intellij
- Run App.java as a Java application
- You can change the application parameters by modifying the /wordFilter/src/main/java/com/hbe/wordFilter/utils/Constants.java class (DEFAULT_INPUT_FILE - DEFAULT_OUTPUT_FILE - DEFAULT_FILTER)
- Launch mvn clean install to build the project and generate a jar with embedded dependencies
- You can also run the application from command line : java -jar {input_file_path} {output_file_path} {filter}
