# You don’t need {} for method bodies (use intend instead), no semicolon, no () in if conditions.
def divide(a, b):
    if b is 0:
        raise ValueError("Dude, you can't divide {} by {}".format(a, b))
    return a / b

# more intuitive than Java's ternary operator
parameter = "Peter"
name = "Unknown" if parameter is None else parameter

# multi-line strings. praise the lord!
description = """This is my
multi-line string. """

# chained comparison
if 200 <= status_code < 300:
    print("success")
