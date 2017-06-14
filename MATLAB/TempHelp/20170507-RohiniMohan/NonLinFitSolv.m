clear
load('Data.mat')
I_d=5.3e-7;
I_0=8.14e-7;

y = log((I_out - I_d)/(I_0-I_d));
Y = log(-y);
x = t;

F = @(v,x) -log(v(1))+v(2)*log(x);
v0 = [ 10; 0.5];


[v,resnorm,~,exitflag,output] = lsqcurvefit(F,v0,x,Y)

Tau = v(1);
Beta = v(2);