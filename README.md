# Microservices Bank Application
#Spring boot #Microservices #Spring cloud #OpenFeign #Docker #Hibernate #Mockito #HTML/CSS #Thymleaf 
#Bootstrap #JUnit #RESTAPI #PostgreSQL #Flyway #Spring security #mvc

# Available
### Login and Register system
* Checking before register by 'login' if user already exist
* Password encoding
* User authentication principal via Spring Security
### Open account system
* At the time of registration we receive the main account
(validation if you registered while the account manager was down,
you will still get the main account after runs again)
* Several types of account e.g. currency account, savings account
### Transfer money from user to user
* Checking that the user want to transfer money to himself and if user which we send money already exist
* Limit for that kind of transaction is 20000 each
* Transfer money by account number
* Account number starts with country code e.g. '€' account -> starts with 'EU' etc.
### Deposit and withdraw money
* There is a limit for withdraw money to 5000 and 15000 for deposit each
* Checking if we have enough money to withdraw

#
## still in progress... 
#### Exchange system
#### *Credit system
#### Close account
#### Admin Panel
#### Spending money system

#### ports: 5432 - 8080 - 8090 - 8000 - 8010
