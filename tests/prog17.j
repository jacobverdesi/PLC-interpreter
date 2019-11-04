//Combined Phase 2 components
Integer a = 10;
Integer b = 0;
String accept = "";

if( a > b * 3 == 3 ) { accept = concat( accept, "P"); b = 11; }
else { print( "Failed" ); }
if( b <= a + 4 / 2 != 5 ) { accept = concat( accept, "a" ); }
else { print( "Failed" ); }
Integer x = 0;
for( Integer c = 0; c != 5; c = c + 1) { x = c >= 4; }
if( 2 / 2 == x ) { accept = concat( accept, "s" ); }
else { print( "Failed" ); }
Integer y = 5;
while( y >= 0 ) { y = y - 1; }
if( y == -1 ){ accept = concat(accept, "s" ); }
else { print( "Failed" ); }
while( y == 0 ) { print( "Failed" ); y = 1; }
concat( accept, "e" );
if( b - a + y == 0 ) { concat( accept, "d" ); }
print( accept );