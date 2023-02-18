# Microservices Bank Application
#Spring boot #Microservices #Spring cloud #OpenFeign #Docker #Hibernate #Mockito #HTML/CSS #Thymleaf 
#Bootstrap #JUnit #RESTAPI #PostgreSQL #Flyway #Spring security #mvc

# Available

### Credit Logic
* Before taking credit we need to fill proposal
* There are several simple validation e.g. if commission is more than we have
  money on account we can't approve a credit proposal, 
  also if we already have that kind of credit in our account
* If everything is alright we are redirected to next view in which we have a choice
to approve or cancel our proposal, there are detailed information about the credit 
e.g. interest, monthly fee etc.
* If we already have an opened proposal, we cannot make another one before that accepted
* Even if we cancel proposal, every attempt are stored in the database
* At any time we can come back to a request that we did not approve for some reason
* If monthly fee is below half your salary that is 100% chance to get a credit, but if the monthly fee is greater than:
2. 50% your salary, rejected chance: 50%
3. 75% your salary, rejected chance: 80% 
4. 100% your salary, rejected chance: 100%
* We have to pay a commission, which is calculated from amount of credit, min is 50zł or other currency,
 if commission is more than 50zł we pay as how much as has been calculated
* Credit below 10 000zł or other currency has 0% commission
* Minimum amount which we can take it is 1000

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
* Money exchange during transfer money from user to user by currency api
* Checking that the user want to transfer money to himself and if user which we send money already exist
* Limit for that kind of transaction is 20000 each
* Transfer money by account number
* Account number starts with country code e.g. '€' account -> starts with 'EU' etc.
### Deposit and withdraw money
* There is a limit for withdraw money to 5000 and 15000 for deposit each
* Checking if we have enough money to withdraw

#
## still in progress...
#### *Mortgage loan
#### Close account
#### Spending money system

#### ports: 5432 - 8080 - 8090 - 8000 - 8010
