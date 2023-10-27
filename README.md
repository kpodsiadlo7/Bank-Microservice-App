# Microservices Bank Application
#Spring boot #Microservices #Spring cloud #OpenFeign #Docker #Hibernate #Mockito #HTML/CSS #Thymleaf 
#Bootstrap #JUnit #RESTAPI #PostgreSQL #Flyway #Spring security #mvc

# Available

### Credit Logic
* Before applying for credit, a proposal must be submitted.
* Various simple validations are in place; for instance, if the commission exceeds the available
  account balance, the credit proposal cannot be approved. Also, if a similar credit is already
  active in the account, a new proposal won't be accepted.
* Upon successful validation, you are redirected to the next view, which provides options to
  either approve or cancel the proposal. Detailed information regarding the credit, such as interest
  rates and monthly fees, is presented.
* It's important to note that if there's an existing active proposal, a new one cannot be
  initiated until the previous one is either accepted or canceled.
* Even if a proposal is canceled, every attempt is recorded in the database.
* At any time, you can revisit a previously unapproved request.
* The likelihood of credit approval depends on the relationship between the monthly fee and your salary:
1. If the monthly fee is less than 50% of your salary, the chance of rejection is 0%.
2. If the monthly fee is between 50% and 75% of your salary, the rejection chance is 50%.
3. If the monthly fee equals or exceeds 75% of your salary, the rejection chance rises to 80%.
4. If the monthly fee equals 100% of your salary, the rejection chance is 100%.
* A commission is applicable, calculated based on the credit amount. The minimum commission is 50 zł
  (or the equivalent in other currencies). If the calculated commission exceeds 50 zł, you will be
  charged the calculated amount.
* There's an option to take advantage of a promotion for a 0% commission on credits less than 10,000.
* The minimum credit amount that can be requested is 1,000.
* Each account can have only one active currency.

### Login and Registration System
* Prior to registration, the system checks if the user already exists using the 'login' information.
* Passwords are securely encoded.
* User authentication is managed through Spring Security.
### Account Management System
* During the registration process, users receive their primary account. The system also handles cases
  where registration occurs while the account manager is offline; users will still receive their primary
  account once it's operational.
* Various types of accounts are available, including currency accounts and savings accounts.
### User-to-User Money Transfer
* Money exchange is conducted during user-to-user money transfers using a currency API.
* The system checks to ensure that users do not transfer money to themselves and that the recipient user exists.
* There's a transaction limit of 20,000 units for this type of transfer.
* Money can be transferred using account numbers, which start with country codes (e.g., '€' account -> starts with 'EU,' etc.).
### Deposit and Withdrawal of Funds
* There are limits for withdrawals (up to 5,000 units) and deposits (up to 15,000 units).
* The system performs checks to verify the availability of funds for withdrawals.

#
## still in progress...
#### *Mortgage loan
#### Close account
#### Extend credit logic
#### Spending money system

#### ports: 5432 - 8080 - 8090 - 8000 - 8010
