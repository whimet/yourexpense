from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext import webapp
from google.appengine.ext import db
import os
from google.appengine.api import users
from google.appengine.ext.webapp import template
import datetime

class Expense(db.Model):
    user = db.UserProperty()
    datetime = db.DateTimeProperty(auto_now_add=True)
    amount = db.FloatProperty()
    category = db.CategoryProperty()
    comment = db.StringProperty()

class ExpenseHandler(webapp.RequestHandler):
    def get(self):
        expense_query = Expense.all().order('-datetime')
        expenses = expense_query.fetch(100)

        template_values = { 'expenses': expenses }
        path = os.path.join(os.path.dirname(__file__), '../templates/expenses.json')
        self.response.out.write(template.render(path, template_values))
        
    def post(self):
        expense = Expense()

        if users.get_current_user():
            expense.user = users.get_current_user()

        expense.amount = float(self.request.get('amount'))
        expense.category = self.request.get('category')
        expense.comment = self.request.get('comment')
        datestr = self.request.get('date')
        if len(datestr) >= 8 and datestr.isdigit():
            expense.datetime = datetime.datetime(int(datestr[0:4]), int(datestr[4:6]), int(datestr[6:8]))
            
        key = expense.put()

        if self.request.get('redirect') == 'true':
            self.redirect('/')
        else:
            self.response.set_status(201)
            self.response.out.write("{'id' : " + str(key.id()) + "}")

        
class MainPage(webapp.RequestHandler):
    def get(self):
        expense_query = Expense.all().order('-datetime')
        expenses = expense_query.fetch(100)

        if users.get_current_user():
            url = users.create_logout_url(self.request.uri)
            url_linktext = 'Logout'
        else:
            url = users.create_login_url(self.request.uri)
            url_linktext = 'Login'

        template_values = {
            'expenses': expenses,
            'url': url,
            'url_linktext': url_linktext,
            }

        path = os.path.join(os.path.dirname(__file__), '../templates/index.html')
        self.response.out.write(template.render(path, template_values))

application = webapp.WSGIApplication(
                                     [('/', MainPage),
                                      ('/expense', ExpenseHandler)],
                                     debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()