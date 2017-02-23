Start = 20;
End = 120;
maxRange = 0.7;
x=20:0.1:120;
y = ExperimentFiveFactor( Start, End, maxRange, x );
plot (x,y)