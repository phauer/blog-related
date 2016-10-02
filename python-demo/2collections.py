# Python is just awesome when it comes to collections

# nice literals for list, tuple, sets, dicts
my_list = [1, 2, 3]
my_tuple = (1, 2)
my_set = {1, 2, 2}
my_dict = {"a": 1, "b": 2}
# this collection literals makes dealing with JSON a pleasure.
# this is also the reason why the Python driver for MongoDB makes definitely more fun.

# contains
contains_element = 1 in my_list  # True
contains_key = "a" not in my_dict  # False

# access
value = my_list[0]
value2 = my_dict["a"]

# iteration
for element in my_list:
    print(element)

for index, element in enumerate(my_list):  # foreach with index!
    print(index, element)

for key, value in my_dict.items():  # how cool is that?!
    print(key, value)

# list can be used as stacks...
print(my_list.pop())  # 3 (remove and return element)
# ...and as queues
print(my_list.pop(0))  # 1 (remove and return first element)

# == List Comprehension ==
# That's my personal kick-ass feature of Python
# It's like map() and filter() of the Java 8 Stream API, but much better.
# syntax: [<map_function> for <element> in <collection> if <condition>]
# example:
names = ["peter", "paul", "florian", "albert"]
# filter for names starting with "p" (filter()) and upper case them (map())
result = [name.upper() for name in names if name.startswith("p")]
print(result)  # ["PETER", "PAUL"]
# and it returns... a new list! no annoying collect(Collectors.toList()) boilerplate.
# i really love this powerful and concise syntax.

# slice syntax for extracting parts of a collection
# syntax: collection[startIndex:stopIndex(:step)]
# startIndex defaults to 0; stopIndex defaults to len(col); step defaults to 1, negative step reverses direction of iteration
print(names[1:2])  # paul
print(names[1:])  # all except the first: paul, florian, albert
print(names[:1])  # get first element: peter
print(names[:])  # copy whole collection
print(names[::-1])  # reverse list: ['albert', 'florian', 'paul', 'peter']
# works also for strings
print("hello nice world"[6:10])  # nice
