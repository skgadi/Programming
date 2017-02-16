ph = 1:0.01:13;
temp = 0:0.1:100;
[PH, TEMP]=meshgrid(ph,temp);
Y = ExperimentTwoFactor(PH, TEMP);
surf(PH, TEMP, Y);