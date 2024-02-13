# DD2480-Assignment-2-CI


# Setting up the project
- Make sure to have java openjdk 21 installed
- Make sure you have [ngrok](https://ngrok.com/docs/getting-started/) installed 
- Make sure you have the latest version of [maven](https://maven.apache.org/download.cgi) installed 
- Create a GitHub fine-grain [Personal Access Token](https://github.com/settings/tokens?type=beta) for the [repo](https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-C) with only permission for "Read and Write access to commit statuses" and "Read access to metadata". Place the token in a file named "githubPAT" in the root folder of the project. This is required for authenticating the status updates.

## Running the project
- Clone the repo and make sure you are inside the CI subfolder
- Run **mvn test** to confirm that everything is working
- Run the program using intellij or VSCode or using the run command
- on a seperate terminal run **ngrok http 8080**

For more details on  setting up the server with webhooks look [here](https://github.com/KTH-DD2480/smallest-java-ci/blob/master/README.md)

## CI feature #2 - testing

### Implementation

To test the program, the code calls maven with the ProcessBuilder class. The function then takes as an input parameter the specific test case, a specific test class or - if the input is empty - all tests. After these tests are run, another function will check a file where the ersults are stored to check whether the tests passed by checking for BULILD SUCCESS in this file.  The code then returns the boolean evaluation of said test, or will throw an exception if the test fails. 

### Testing
The testing process of this function is done with the aide of dummy tests. These dummy tests have one true assertion and one false assertion. We then run specific test cases for these dummy tests to see if the function also returns the correct assertions.

## CI feature #3 - notification

### Implementation

The status notification ([found in StatusNotification](src/main/java/group8/StatusNotification.java)) is implemented using curl POST request which is executed by java RunTime. 

The request consists of: 
- The API path consisting of the repo, the owner and the full 40 character SHA. 
- A GitHub PAT for authentication. 
- A JSON object body with a state, target_url, description, and context field. This message is in turn generated by the createStatusMessage function, which takes in the outcome of the compilation and test suit.

### Testing
The test suit for createStatusMessage consists of assuring that the content of the JSON message is correct for the combinations: both compilation and tests passes, compilation passes and tests fails and compilation fails.

The test suit for statusNotification consists of a check that the GitHub PAT exists, as well as that a succesfull commit returns success, a failing commit returns failure and that an incorrect PATH (incorrect SHA) results in state not being found.

# Team evaluation


# Statement of contribution

**Mert Demirsü** - 

**Melissa Mazura** - 

**Marcus Odin** - 

**Atheer Salim** - 

**Jonatan Tuvstedt** - 