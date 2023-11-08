# Microservices Bank Application
#Spring boot #Microservices #Spring cloud #OpenFeign #Docker #Hibernate #Mockito #HTML/CSS #Thymleaf 
#Bootstrap #JUnit #RESTAPI #PostgreSQL #Flyway #Spring security #mvc

[English Version](#description-in-the-english-version)

# Opis aplikacji
### Logika Kredytowa
* Przed złożeniem wniosku o kredyt, konieczne jest przedstawienie propozycji i wypełnienie wniosku.
* Wprowadzono różne walidacje; na przykład, jeśli prowizja przekracza dostępny stan konta, wniosek kredytowy nie może zostać zatwierdzony. Ponadto, jeśli na koncie istnieje już aktywny kredyt o podobnych warunkach, nowa propozycja nie zostanie przyjęta.
* Po udanej walidacji, następuje przekierowanie do kolejnego widoku, który oferuje opcje zatwierdzenia lub anulowania propozycji. Prezentowane są szczegółowe informacje dotyczące kredytu, takie jak oprocentowanie i opłaty miesięczne.
* Warto zauważyć, że jeśli istnieje aktywna propozycja kredytowa, nową nie można zainicjować, dopóki poprzednia nie zostanie zaakceptowana lub anulowana.
* Nawet jeśli propozycja zostanie anulowana, każda próba jest rejestrowana w bazie danych.
* W każdej chwili można powrócić do wcześniej niezaakceptowanego lub otwartego wniosku.
* Szansa na zatwierdzenie kredytu zależy od relacji między opłatą miesięczną a Twoją pensją:
1. Jeśli opłata miesięczna wynosi mniej niż 50% Twojej pensji, szansa na odrzucenie to 0%.
2. Jeśli opłata miesięczna wynosi od 50% do 75% Twojej pensji, szansa na odrzucenie to 50%.
3. Jeśli opłata miesięczna wynosi 75% lub więcej Twojej pensji, szansa na odrzucenie wzrasta do 80%.
4. Jeśli opłata miesięczna wynosi 100% Twojej pensji, szansa na odrzucenie wynosi 100%.
* Naliczana jest prowizja, obliczana na podstawie kwoty kredytu. Minimalna prowizja wynosi 50 zł (lub równowartość w innych walutach). Jeśli obliczona prowizja przekracza 50 zł, zostanie naliczona odpowiednia kwota.
* Istnieje opcja skorzystania z promocji na 0% prowizji przy kredytach poniżej 10 000 zł.
* Minimalna kwota kredytu, jaką można wnioskować, wynosi 1 000 zł.
* Każde konto może mieć tylko jedną aktywną walutę.
### System Logowania i Rejestracji
* Przed rejestracją system sprawdza, czy użytkownik już istnieje w bazie danych.
* Hasła są zakodowane.
* Autoryzacja użytkownika jest zarządzana za pomocą Spring Security.
### System Zarządzania Kontem
* Podczas procesu rejestracji użytkownicy otrzymują swoje podstawowe konto. System obsługuje również przypadki rejestracji, gdy menedżer konta był niedostępny w momencie rejestracji; użytkownicy otrzymają swoje podstawowe konto, gdy stanie się on ponownie dostępny.
* Dostępne są różne rodzaje kont, w tym konta walutowe i konta oszczędnościowe.
### Przelew Pieniędzy Od Użytkownika Do Użytkownika
* Przelew pieniędzy odbywa się za pomocą interfejsu API walutowego z uwzględnieniem aktualnego kursu dla walut.
* System sprawdza, czy użytkownicy nie przelewają pieniędzy sobie samym oraz czy odbiorca istnieje.
* Dla tego rodzaju przelewu istnieje ograniczenie transakcji do 20 000 zł.
* Przelewy odbywają się za pomocą numerów kont bankowych, które zaczynają się od kodów krajów (np. konto '€' zaczyna się od 'EU', itp.).
### Wpłaty i Wypłaty Środków
* Istnieją limity dla wypłat (do 5 000 zł) i wpłat (do 15 000 zł).
* System automatycznie sprawdza, czy dostępne są pieniądze do wypłaty.


# Description in the English version

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

#### ports: 5432 - 8080 - 8090 - 8000 - 8010
