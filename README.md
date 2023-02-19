# springboot-bank-api
Trying to mimic a banking application's API using Spring Boot &amp; Hibernate

**Open Bank Acccount**
Endpoint: `POST localhost:8080/accounts/open-bank-account
Sample Payload: 
```
{

	"firstName": "Jonathan",
	"lastName": "Francisco",
	"mobileNumber": "639123123123"
}
```


**Get Bank Acccount**
Endpoint: `GET localhost:8080/accounts`


**Deposit Money**
Endpoint: `POST localhost:8080/deposits
Sample Payload: 
```
{
	"bankAccountNo": "47be13b1-3300-4095-a658-37ea1bdcccfb",
	"amount": 500.88,
	"type": "CASH"
}
```


**Withdraw Money**
Endpoint: `POST localhost:8080/withdrawals
Sample Payload: 
```
{

	"bankAccountNo": "47be13b1-3300-4095-a658-37ea1bdcccfb",
	"amount": 100000

}
```


**Transfer Money**
Endpoint: `POST localhost:8080/transfers
Sample Payload: 
```
{
	"sourceBankAccountNo": "47be13b1-3300-4095-a658-37ea1bdcccfb",
	"destinationBankAccountNo": "347e5f45-ed1b-4508-aa8a-c4701cfbb4ae",
	"amount": 1000
}
```
