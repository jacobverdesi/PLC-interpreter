Integer x = 5;
Integer y = 6;
Double a = 5.5;
Double b = 6.6;
String j = "Hello";
String k = "Goodbye";

if( x > y ) { print("Fail"); }
if( x < y ) { print("Pass"); }
if( x == 5 ){ print("Pass"); }
else { print("Fail"); }
if( x == y ){ print("Fail"); }
else { print("Pass"); }
if( x <= y ){ print("Pass"); x = 6; }
else { print("Fail"); }
if( x <= y ){ print("Pass"); }
else { print("Fail"); }
if( j == k ){ print("Fail"); }
else { print("Pass"); }
if( concat(j, k) == "HelloGoodbye" ){ print("Pass"); }
else { print("Fail"); }