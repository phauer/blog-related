import datetime
today = datetime.date.today()
some_day = datetime.date(2016, 9, 15)
print(today - some_day)  # "17 days, 0:00:00". difference between dates.

set1 = {1, 2, 3}
set2 = {3, 4, 5}
print(set2 - set1)  # {4, 5}. difference between sets

# Let's define our own overloading
# Therefore, we use Protocols. Protocols define method signatures. If you implement these methods, you can use certain operators.
# e.g. for "==" we need to implement "__eq__"
class User:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __eq__(self, other):
        return self.name is other.name and self.age is other.age

print(User("Hauke", 28) is User("Peter", 30))  # False

# e.g. for "list[element]" we need to implement "__getitem__"
class UserAgeFinder:
    def __init__(self):
        self.user_list = [User("Hauke", 28), User("Peter", 30), User("Hugo", 55)]

    def __getitem__(self, name):
        return [user.age for user in self.user_list if user.name is name]

finder = UserAgeFinder()
print(finder["Hauke"])  # [28]

