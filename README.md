# DD2480-Assignment-2-CI


# how to run the server
- make sure to have java openjdk 21 installed, since that is the java version used for the project
- make sure you have installed ngrok and set it up on your machine, use following [link](https://ngrok.com/docs/getting-started/)
- make sure you have installed latest version of maven, you can use the following [link](https://maven.apache.org/download.cgi)
- make sure to create a GitHub fine-grain [Personal Access Token](https://github.com/settings/tokens?type=beta) for the [repo](https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-C) with only permission for "Read and Write access to commit statuses" and "Read access to metadata". Place the token in a file named githubPAT in the root folder of the project. This is required for status updates.
- Strongly advise to use intellij idea as the your IDE since that makes everything easier.
  
- clone the repo and make sure you are inside the CI subfolder
- now try to run **mvn compile** everything should work
- you can run the program using intellij using the run command
- on a seperate terminal run **ngrok http 8080**

Now everything should work, look [here](https://github.com/KTH-DD2480/smallest-java-ci/blob/master/README.md) for more details on setting up the server with webhooks

## CI feature #3 - notification

The status notification is implemented by running a curl command requesting a status update on the commit in question. This requires a GitHub Personal Access Token, as well as the repo owner, repo name and full SHA of the commit. The status and description is dependent on whether the commit compiles and if all tests are passed.
