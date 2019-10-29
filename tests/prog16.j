//Makes sure incompatible type reassignment doesn't work
Integer fail = 3;
print( fail );
fail = "Nope";
print( fail );