from google.appengine.ext import db
import os
from google.appengine.api import users
from google.appengine.ext.webapp import template

class Expense(db.Model):
    user = db.UserProperty()
    datetime = db.DateTimeProperty()
    amount = db.FloatProperty()
    category = db.CategoryProperty()
    comment = db.StringProperty()

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

        path = os.path.join(os.path.dirname(__file__), 'index.html')
        self.response.out.write(template.render(path, template_values))


