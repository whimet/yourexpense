# Create an expense record #

## Client request ##

POST http://your-expense.appengine.com/expense

> Post key-value pairs:

  * amount=50
  * category=FOOD
  * datetime=2010070922 or blank, which means current time
  * comment=blablabla

## Server response ##

  * 201
  * {'id' : 1001}

## Example ##

  * curl http://your-expense.appspot.com/expense -d "amount=99&category=test&comment=from\_curl"
  * {'id' : 3001}

# List all expense record #

## Client request ##

GET http://your-expense.appengine.com/expense

> Parameters:

  * startdate=20100709
  * todate=20100709 or blank, which means current date

## Server response ##

  * 200
  * [{'id' : '1001', 'category' : {'id' : '3', 'name' : 'FOOD'}, 'amount' : 50, 'comment' : 'blablabla', 'datetime' : '2010070922' }, {...}]