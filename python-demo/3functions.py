def copy(source_file, target_file, override):
    pass  # imagine some code here...

# unclear what the parameters mean:
copy("~/file.txt", "~/file2.txt", True)
# named parameters make method calls readable!
copy(source_file="~/file.txt", target_file="~/file2.txt", override=True)
# it's also more secure, because an error is thrown, if the argument name doesn't exist

# default parameters! no silly constructor chaining.
def copy2(source_file, target_file, override = True):
    pass  # imagine some code here...
copy2(source_file="~/file.txt", target_file="~/file2.txt")

# functions are first class citizens. You can assign them to variables, return and pass them around
my_copy = copy2

# lambdas
get_year = lambda date: date.year
import datetime
today = datetime.date.today()
some_day = datetime.date(2010, 9, 15)
for date in [today, some_day]:
    print(get_year(date))  # 2016, 2010


# multiple return values via automatic tuple packing and unpacking
def multiple_return():
    return 1, 2, True  # will be packed to a tuple
print(multiple_return())  # (1, 2, True)

first, second, third = multiple_return()  # unpacking
print(first, second, third)  # 1 2 True
