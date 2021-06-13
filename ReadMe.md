# PetStore simple API testing framework

A simple RestAssured based HTTP API framework, intended for service / environment
sanity tests in api level.
User data (credentials / secureIds), for users who appear in multiple tests, 
is held in the configuration files.
All tests should be non-destructive (no removal of user data, or change of user data that is 
expected to obstruct the next run of the test), and are expected to use existing users.
Given that scope of test, success criteria, business requirements are not provided, 
the test approaches should focus on api itself and CRUD operations.


General guidelines:
1. Adding new APIs should be as simple as possible, on the expense of "good code structure"
2. Simple response validations can be performed in the test (such as Hamcrest Matchers). 
   If extracting data from the response is not simple - provide a method for that 
   (in the API, or as a standalone response object)
3. All APIs should return (at the minimum) a RestAssured response object
4. When applicable - add schema validation could be tested.

# Structure
Configuration files:
1. One file per environment (STAGE and PRODUCTION)
2. Holds the environment base URL
3. Holds user data for users that appear in multiple tests


Tests files:
Simple TestNG tests

API classes (Api package):
Holds all the API / response related classes

Additional DTOs (e.g. Inject3Dtos package):
Holds API specific DTOs


# TestRail integration
 
The existing plan and the plan-to-clone can be provided via a TestNG xml file as parameters.  
(The parameters can be provided from CI / command line as well, pointing to the TestNG xml file).

## Test methods and assertions
If you wish a test case to report results to TestRail, the following should be considered:
- All the test case ids (from the TestRail suites) that are going to be covered by the test method
- Where in the test method (mainly, in relation to the assertions) are the test cases actually being checked


# Running from Jenkins
- Source Code Management - Git
- Repository URL - https://github.com/ismailkoembe/PetstoreApiAutomationTestNgProject.git
- Credentials - configure for the relevant user, can be done via the job configuration
- Branch Specifier - */master
- Bindings -> Secret Text -> Variable: environment, token, TestRail credentials (in future) 
- Build -> Top level Maven targets -> Goals: clean install -DtestngFile=/{Jenkins work path}/{job name}/src/test/Resources/{XML to run}.xml -DmyEnv={Env}


# API Product Test Strategy: The main objectives
- to ensure that the implementation is working as specified according to the requirements specification.
- to ensure that the implementation is working correctly as expected — no bugs.
- to prevent regressions between code merges and releases.
# Test scenario categories
- Basic positive tests
   1. Response is a well-formed JSON object
   2. Response structure is according to data model 
      (schema validation: field names and field types are as expected.
- Extended positive testing with optional parameters
- Negative testing with valid input
- Negative testing with invalid input
- Destructive testing (N/A for this project)
- Security, authorization, and permission tests (N/A for this project)
    1. Positive: ensure API responds to correct authorization via all agreed auth
      methods – Bearer token, etc. – as defined in spec
    2. Negative: ensure API refuses all unauthorized calls
- Performance (N/A for this project)
    1. Check API response time, latency (in isolation and under load).  
#Tools
- Postman 
- Adding API Integration tests with the programming language in use,
here in Java using TestNG Library.