# let's define a User class with the properties name and age
class User:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def introduce_yourself(self):
        print("Hi, I'm {}, {} years old".format(self.name, self.age))

# create a User
user = User("Hauke", 28)
print(user.name)  # Hauke
user.introduce_yourself()  # Hi, I'm Hauke, 28 years old

# by the way there is also Inheritance and Polymorphism
