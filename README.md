# Alexa Workshop in Java

There are several skills here that need an implementation for them to work, the templates are there as are the unit tests to verify whether they work. The skills can be implemented in any way as long as the tests verify the skill works according to the requirements.

After finishing the assignments you should be able to develop and publish Alexa skills on your own.

## Setup

The setup of the workshop requires Java 8 and Maven to be installed.

### Prerequisites

* Install Java 8
* Install maven

### Obtain workshop

Fork or clone the workshop repository

### Build

Open a terminal in the directory of the skill you want to build:
```
alexa-workshop-java/FinancialPlanning
```
To Build the skill run the following command:
```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
```
The `.jar` file will be stored in the following location:
```
alexa-workshop-java/FinancialPlanning/target/FinancialPlanning-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Unit testing

Open a terminal in the directory of the skill you want to test:
```
alexa-workshop-java/FinancialPlanning
```
Run maven:
```
mvn clean install
```

## Testing Skills
_Amazon accounts are required to deploy and test skills_

To test whether the skills would work on Alexa itself the skill will have to be deployed to Amazon Lambda and an Emulator can be used to see if it works.

### Deployment
_Please note that an Amazon Lambda account is required_

The following tutorial by Matthias Shapiro perfectly explains how to deploy a C# Amazon Alexa skill to Amazon Lambda: 

```
http://matthiasshapiro.com/2017/02/10/tutorial-alexa-skills-in-c-setup/
```
 
### Emulation
_Please note that an Amazon account is required_

 
To emulate whether a skill works without using an actual Amazon Echo or other hardware, the following online emulator can be used:

```
https://echosim.io/
``` 
